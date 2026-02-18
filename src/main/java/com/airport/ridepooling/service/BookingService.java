package com.airport.ridepooling.service;

import com.airport.ridepooling.dto.BookingCreateRequest;
import com.airport.ridepooling.dto.BookingResponse;
import com.airport.ridepooling.mapper.EntityMapper;
import com.airport.ridepooling.model.BookingRequest;
import com.airport.ridepooling.model.Cab;
import com.airport.ridepooling.repository.BookingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRequestRepository bookingRequestRepository;
    private final EntityMapper entityMapper;

    public BookingResponse createBooking(BookingCreateRequest requestDTO) {
        // Convert input request to DB Entity
        BookingRequest request = entityMapper.toBookingEntity(requestDTO);

        // Automatic timestamp and initial status
        request.setRequestTime(LocalDateTime.now());
        request.setStatus(BookingRequest.BookingStatus.PENDING);

        // Save and return formal response
        BookingRequest savedRequest = bookingRequestRepository.save(request);
        return entityMapper.toBookingResponse(savedRequest);
    }

    // Retrieves all passengers waiting for a cab.
    public List<BookingResponse> getPendingBookings() {
        return bookingRequestRepository.findByStatus(BookingRequest.BookingStatus.PENDING).stream()
                .map(entityMapper::toBookingResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse cancelBooking(Long bookingId) {
        //Find the booking
        BookingRequest request = bookingRequestRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        //Prevent double-cancellation
        if (request.getStatus() == BookingRequest.BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        //Capacity Restoration Logic
        // If the passenger was already assigned to a cab, we must give the space back
        if (request.getStatus() == BookingRequest.BookingStatus.POOLED && request.getAssignedCab() != null) {
            Cab cab = request.getAssignedCab();

            // Give back seats and luggage space
            cab.setRemainingSeats(cab.getRemainingSeats() + request.getSeatsRequired());
            cab.setRemainingLuggage(cab.getRemainingLuggage() + request.getLuggageQuantity());

            // If the cab was FULL, it's now PARTIAL (accepting new people again)
            if (cab.getStatus() == Cab.CabStatus.FULL) {
                cab.setStatus(Cab.CabStatus.PARTIAL);
            }
        }

        //Update status and remove the cab link
        request.setStatus(BookingRequest.BookingStatus.CANCELLED);
        request.setAssignedCab(null);

        return entityMapper.toBookingResponse(bookingRequestRepository.save(request));
    }
}
