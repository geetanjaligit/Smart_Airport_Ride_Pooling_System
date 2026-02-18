package com.airport.ridepooling.dto;

import com.airport.ridepooling.model.BookingRequest.BookingStatus;
import lombok.*;
import java.time.LocalDateTime;

//The full record of a booking as stored in our system.
//Includes the time it was made and its current status.

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private String passengerName;
    private Double pickupLat;
    private Double pickupLng;
    private Double destLat;
    private Double destLng;
    private Integer seatsRequired;
    private Integer luggageQuantity;
    private Double price;
    private LocalDateTime requestTime;
    private BookingStatus status;
    private String assignedCabPlate;
}
