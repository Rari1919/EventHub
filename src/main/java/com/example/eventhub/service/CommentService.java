package com.example.eventhub.service;

import com.example.eventhub.model.Comment;
import com.example.eventhub.model.Event;
import com.example.eventhub.model.User;
import com.example.eventhub.repository.CommentRepository;
import com.example.eventhub.repository.EventRepository;
import com.example.eventhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public List<Comment> getCommentsByEventId(Long eventId) {
        return commentRepository.findByEventIdOrderByCreatedAtDesc(eventId);
    }

    public Comment addComment(Long userId, Long eventId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (content == null || content.isBlank()) {
            throw new RuntimeException("Comment content cannot be empty");
        }
        Comment comment = Comment.builder()
                .user(user)
                .event(event)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        return commentRepository.save(comment);
    }
}
