package com.airport.ridepooling.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking_requests", indexes = {
        @Index(name = "idx_booking_status", columnList = "status"),
        @Index(name = "idx_booking_destination", columnList = "destLat, destLng")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private String passengerName;

    /**
     * Why separate Lat and Lng?
     * Standard GPS coordinates use Latitude and Longitude.
     * Separating them makes it easier to run calculations like "Distance".
     */
    @Column(nullable = false)
    private Double pickupLat;

    @Column(nullable = false)
    private Double pickupLng;

    @Column(nullable = false)
    private Double destLat;

    @Column(nullable = false)
    private Double destLng;

    @Column(nullable = false)
    private Integer seatsRequired;

    @Column(nullable = false)
    private Integer luggageQuantity;

    @Column
    private Double price;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "cab_id")
    private Cab assignedCab;

    public enum BookingStatus {
        PENDING, POOLED, COMPLETED, CANCELLED
    }
}
