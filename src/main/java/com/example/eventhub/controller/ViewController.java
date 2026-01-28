package com.example.eventhub.controller;

import com.example.eventhub.dto.CreateBookingRequest;
import com.example.eventhub.model.Booking;
import com.example.eventhub.model.Comment;
import com.example.eventhub.model.Event;
import com.example.eventhub.model.Favorite;
import com.example.eventhub.service.BookingService;
import com.example.eventhub.service.CommentService;
import com.example.eventhub.service.EventService;
import com.example.eventhub.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final EventService eventService;
    private final BookingService bookingService;
    private final FavoriteService favoriteService;
    private final CommentService commentService;

    @GetMapping("/")
    public String home(Model model) {
        List<Event> events = eventService.getAllEvents().stream().limit(6).toList();
        model.addAttribute("events", events);
        return "home";
    }

    @GetMapping("/events-page")
    public String events(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/events-page/{id}")
    public String eventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        List<Comment> comments = commentService.getCommentsByEventId(id);
        model.addAttribute("event", event);
        model.addAttribute("comments", comments);
        return "event-details";
    }

    @GetMapping("/favorites-page/{userId}")
    public String favorites(@PathVariable Long userId, Model model) {
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(userId);
        model.addAttribute("favorites", favorites);
        return "favorites";
    }

    @GetMapping("/tickets-page/{userId}")
    public String tickets(@PathVariable Long userId, Model model) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        model.addAttribute("bookings", bookings);
        return "tickets";
    }

    @GetMapping("/login-page")
    public String login() {
        return "login";
    }

    @PostMapping("/events-page/{id}/book")
    public String bookTicket(@PathVariable Long id, @RequestParam Long userId, @RequestParam Integer quantity) {
        CreateBookingRequest request = new CreateBookingRequest(userId, id, quantity);
        bookingService.createBooking(request);
        return "redirect:/tickets-page/" + userId;
    }

    @PostMapping("/events-page/{id}/favorite")
    public String addFavorite(@PathVariable Long id, @RequestParam Long userId) {
        favoriteService.addFavorite(userId, id);
        return "redirect:/events-page/" + id;
    }

    @PostMapping("/events-page/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam Long userId, @RequestParam String content) {
        commentService.addComment(userId, id, content);
        return "redirect:/events-page/" + id;
    }
}