package com.example.eventhub.controller;

import com.example.eventhub.model.Event;
import com.example.eventhub.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody Event event) {
        Event created = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event event) {
        try {
            Event updated = eventService.updateEvent(id, event);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(eventService.searchByTitle(title));
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<Event>> filterByCategory(@RequestParam String category) {
        return ResponseEntity.ok(eventService.filterByCategory(category));
    }

    @GetMapping("/filter/location")
    public ResponseEntity<List<Event>> filterByLocation(@RequestParam String location) {
        return ResponseEntity.ok(eventService.filterByLocation(location));
    }
}
