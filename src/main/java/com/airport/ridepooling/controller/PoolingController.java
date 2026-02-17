package com.airport.ridepooling.controller;

import com.airport.ridepooling.service.PoolingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pooling")
@RequiredArgsConstructor
public class PoolingController {

    private final PoolingService poolingService;

    @PostMapping("/run")
    public ResponseEntity<String> runPooling() {
        poolingService.processPendingBookings();
        return ResponseEntity.ok("Pooling algorithm executed successfully!");
    }
}
