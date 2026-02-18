package com.airport.ridepooling.service;

import com.airport.ridepooling.dto.BookingCreateRequest;
import com.airport.ridepooling.dto.BookingResponse;
import com.airport.ridepooling.mapper.EntityMapper;
import com.airport.ridepooling.model.BookingRequest;
import com.airport.ridepooling.repository.BookingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
