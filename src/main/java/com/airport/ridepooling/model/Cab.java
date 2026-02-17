package com.airport.ridepooling.model;

import jakarta.persistence.*;
import lombok.*;


@Entity 
@Table(name = "cabs") 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class Cab {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer maxLuggage;

    @Enumerated(EnumType.STRING) 
    private CabStatus status;

    public enum CabStatus {
        AVAILABLE, PARTIAL, FULL
    }
}
