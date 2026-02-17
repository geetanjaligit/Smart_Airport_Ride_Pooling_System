package com.airport.ridepooling.service;

import com.airport.ridepooling.model.BookingRequest;
import com.airport.ridepooling.model.Cab;
import com.airport.ridepooling.repository.BookingRequestRepository;
import com.airport.ridepooling.repository.CabRepository;
import com.airport.ridepooling.util.DistanceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/* 
 * This is where the "Greedy" logic lives.
 * We look at all pending requests and try to find a cab for them.
 */
@Service
@RequiredArgsConstructor
public class PoolingService {

    private final BookingRequestRepository bookingRepository;
    private final CabRepository cabRepository;

    //This method runs through every passenger waiting for a ride.
    
    @Transactional
    public void processPendingBookings() {
        List<BookingRequest> pendingRequests = bookingRepository.findByStatus(BookingRequest.BookingStatus.PENDING);
        List<Cab> availableCabs = cabRepository.findAll().stream()
                .filter(c -> c.getStatus() != Cab.CabStatus.FULL)
                .toList();

        for (BookingRequest request : pendingRequests) {
            matchRequestToCab(request, availableCabs);
        }
    }

    private void matchRequestToCab(BookingRequest request, List<Cab> cabs) {
        for (Cab cab : cabs) {
            //Can they fit?
            if (cab.getRemainingSeats() >= request.getSeatsRequired() &&
                    cab.getRemainingLuggage() >= request.getLuggageQuantity()) {

                //If the cab already has people, are they going to the same area?
                if (cab.getStatus() == Cab.CabStatus.PARTIAL) {
                    // Find the first person in this cab to compare destinations
                    BookingRequest firstPassenger = bookingRepository.findAll().stream()
                            .filter(b -> b.getAssignedCab() != null && b.getAssignedCab().getId().equals(cab.getId()))
                            .findFirst().orElse(null);

                    if (firstPassenger != null) {
                        double distance = DistanceUtils.calculateDistance(
                                firstPassenger.getDestLat(), firstPassenger.getDestLng(),
                                request.getDestLat(), request.getDestLng());

                        // If they are more than 10km apart, don't pool them 
                        if (distance > 10.0)
                            continue;
                    }
                }

                //ASSIGN THE RIDE
                assignCabToRequest(request, cab);
                break; // Move to the next passenger
            }
        }
    }

    private void assignCabToRequest(BookingRequest request, Cab cab) {
        // Update Request
        request.setAssignedCab(cab);
        request.setStatus(BookingRequest.BookingStatus.POOLED);
        bookingRepository.save(request);

        // Update Cab Capacity
        cab.setRemainingSeats(cab.getRemainingSeats() - request.getSeatsRequired());
        cab.setRemainingLuggage(cab.getRemainingLuggage() - request.getLuggageQuantity());

        // Update Cab Status
        if (cab.getRemainingSeats() == 0) {
            cab.setStatus(Cab.CabStatus.FULL);
        } else {
            cab.setStatus(Cab.CabStatus.PARTIAL);
        }
        cabRepository.save(cab);
    }
}
