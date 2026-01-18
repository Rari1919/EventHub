package com.example.eventhub.service;

import com.example.eventhub.model.Event;
import com.example.eventhub.model.Favorite;
import com.example.eventhub.model.User;
import com.example.eventhub.repository.EventRepository;
import com.example.eventhub.repository.FavoriteRepository;
import com.example.eventhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public Favorite addFavorite(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Optional<Favorite> existing = favoriteRepository.findByUserIdAndEventId(userId, eventId);
        if (existing.isPresent()) {
            return existing.get();
        }

        Favorite fav = Favorite.builder()
                .user(user)
                .event(event)
                .build();
        return favoriteRepository.save(fav);
    }

    public void removeFavorite(Long userId, Long eventId) {
        Optional<Favorite> existing = favoriteRepository.findByUserIdAndEventId(userId, eventId);
        existing.ifPresent(favoriteRepository::delete);
    }

    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
}
