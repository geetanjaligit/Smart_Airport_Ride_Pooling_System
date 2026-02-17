package com.airport.ridepooling.service;

import com.airport.ridepooling.dto.CabRequest;
import com.airport.ridepooling.dto.CabResponse;
import com.airport.ridepooling.mapper.EntityMapper;
import com.airport.ridepooling.model.Cab;
import com.airport.ridepooling.repository.CabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CabService {

    private final CabRepository cabRepository;
    private final EntityMapper entityMapper;

    public CabResponse registerCab(CabRequest request) {
        // Convert input "request" into a DB Entity
        Cab cab = entityMapper.toCabEntity(request);

        // Initialize capacity fields
        cab.setRemainingSeats(cab.getTotalSeats());
        cab.setRemainingLuggage(cab.getMaxLuggage());

        // Every new cab MUST start as AVAILABLE
        cab.setStatus(Cab.CabStatus.AVAILABLE);

        // Save to DB and convert to formal "response"
        Cab savedCab = cabRepository.save(cab);
        return entityMapper.toCabResponse(savedCab);
    }

    public List<CabResponse> getAllCabs() {
        return cabRepository.findAll().stream()
                .map(entityMapper::toCabResponse)
                .collect(Collectors.toList());
    }

    public List<CabResponse> getAvailableCabs() {
        return cabRepository.findByStatus(Cab.CabStatus.AVAILABLE).stream()
                .map(entityMapper::toCabResponse)
                .collect(Collectors.toList());
    }
}
