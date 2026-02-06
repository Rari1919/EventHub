package com.example.eventhub.controller;

import com.example.eventhub.model.Favorite;
import com.example.eventhub.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Favorite> add(@RequestParam Long userId, @RequestParam Long eventId) {
        Favorite fav = favoriteService.addFavorite(userId, eventId);
        return ResponseEntity.ok(fav);
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam Long userId, @RequestParam Long eventId) {
        favoriteService.removeFavorite(userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Favorite>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteService.getFavoritesByUserId(userId));
    }
}
