package com.airport.ridepooling.dto;

import com.airport.ridepooling.model.Cab.CabStatus;
import lombok.*;

//This is the "Receipt" or "Record" the system returns.
//It includes the generated ID and the current Status.

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabResponse {
    private Long id;
    private String licensePlate;
    private String model;
    private Integer totalSeats;
    private Integer remainingSeats;
    private Integer maxLuggage;
    private Integer remainingLuggage;
    private CabStatus status;
}
