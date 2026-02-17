package com.airport.ridepooling.mapper;

import com.airport.ridepooling.dto.*;
import com.airport.ridepooling.model.BookingRequest;
import com.airport.ridepooling.model.Cab;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    // --- CAB MAPPINGS ---

    public Cab toCabEntity(CabRequest request) {
        return Cab.builder()
                .licensePlate(request.getLicensePlate())
                .model(request.getModel())
                .totalSeats(request.getTotalSeats())
                .maxLuggage(request.getMaxLuggage())
                .build();
    }

    public CabResponse toCabResponse(Cab entity) {
        return CabResponse.builder()
                .id(entity.getId())
                .licensePlate(entity.getLicensePlate())
                .model(entity.getModel())
                .totalSeats(entity.getTotalSeats())
                .remainingSeats(entity.getRemainingSeats())
                .maxLuggage(entity.getMaxLuggage())
                .remainingLuggage(entity.getRemainingLuggage())
                .status(entity.getStatus())
                .build();
    }

    // --- BOOKING MAPPINGS ---

    public BookingRequest toBookingEntity(BookingCreateRequest request) {
        return BookingRequest.builder()
                .passengerName(request.getPassengerName())
                .pickupLat(request.getPickupLat())
                .pickupLng(request.getPickupLng())
                .destLat(request.getDestLat())
                .destLng(request.getDestLng())
                .seatsRequired(request.getSeatsRequired())
                .luggageQuantity(request.getLuggageQuantity())
                .build();
    }

    public BookingResponse toBookingResponse(BookingRequest entity) {
        return BookingResponse.builder()
                .id(entity.getId())
                .passengerName(entity.getPassengerName())
                .pickupLat(entity.getPickupLat())
                .pickupLng(entity.getPickupLng())
                .destLat(entity.getDestLat())
                .destLng(entity.getDestLng())
                .seatsRequired(entity.getSeatsRequired())
                .luggageQuantity(entity.getLuggageQuantity())
                .requestTime(entity.getRequestTime())
                .status(entity.getStatus())
                .assignedCabPlate(entity.getAssignedCab() != null ? entity.getAssignedCab().getLicensePlate() : null)
                .build();
    }
}
