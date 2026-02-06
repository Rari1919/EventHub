package com.example.eventhub.controller;

import com.example.eventhub.model.Comment;
import com.example.eventhub.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Comment>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(commentService.getCommentsByEventId(eventId));
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestParam Long userId,
                                              @RequestParam Long eventId,
                                              @RequestParam String content) {
        Comment saved = commentService.addComment(userId, eventId, content);
        return ResponseEntity.ok(saved);
    }
}
