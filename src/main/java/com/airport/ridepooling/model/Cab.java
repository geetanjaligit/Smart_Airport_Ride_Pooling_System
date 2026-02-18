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

    @Version
    private Long version;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer remainingSeats;

    @Column(nullable = false)
    private Integer maxLuggage;

    @Column(nullable = false)
    private Integer remainingLuggage;

    @Enumerated(EnumType.STRING)
    private CabStatus status;

    public enum CabStatus {
        AVAILABLE, PARTIAL, FULL
    }
}
