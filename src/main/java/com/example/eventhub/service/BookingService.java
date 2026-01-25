package com.example.eventhub.service;

import com.example.eventhub.dto.CreateBookingRequest;
import com.example.eventhub.model.Booking;
import com.example.eventhub.model.Event;
import com.example.eventhub.model.User;
import com.example.eventhub.repository.BookingRepository;
import com.example.eventhub.repository.EventRepository;
import com.example.eventhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public Booking createBooking(CreateBookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }
        if (event.getAvailableSeats() < request.getQuantity()) {
            throw new RuntimeException("Not enough available seats");
        }

        // adjust seats
        event.setAvailableSeats(event.getAvailableSeats() - request.getQuantity());
        eventRepository.save(event);

        Booking booking = Booking.builder()
                .user(user)
                .event(event)
                .quantity(request.getQuantity())
                .bookingDate(LocalDateTime.now())
                .totalPrice(event.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                .ticketCode(UUID.randomUUID().toString())
                .status("CONFIRMED")
                .build();

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getBookingsByEventId(Long eventId) {
        return bookingRepository.findByEventId(eventId);
    }
}
