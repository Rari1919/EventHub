package com.example.eventhub.service;

import com.example.eventhub.model.Event;
import com.example.eventhub.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // copy editable fields
        existing.setTitle(updatedEvent.getTitle());
        existing.setDescription(updatedEvent.getDescription());
        existing.setLocation(updatedEvent.getLocation());
        existing.setEventDate(updatedEvent.getEventDate());
        existing.setCategory(updatedEvent.getCategory());
        existing.setPrice(updatedEvent.getPrice());
        existing.setTotalSeats(updatedEvent.getTotalSeats());
        existing.setAvailableSeats(updatedEvent.getAvailableSeats());
        existing.setImageUrl(updatedEvent.getImageUrl());
        // do not change id or createdBy

        return eventRepository.save(existing);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> searchByTitle(String title) {
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Event> filterByCategory(String category) {
        return eventRepository.findByCategoryIgnoreCase(category);
    }

    public List<Event> filterByLocation(String location) {
        return eventRepository.findByLocationContainingIgnoreCase(location);
    }
}
