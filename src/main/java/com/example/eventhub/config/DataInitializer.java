package com.example.eventhub.config;

import com.example.eventhub.model.Event;
import com.example.eventhub.model.Role;
import com.example.eventhub.model.User;
import com.example.eventhub.repository.EventRepository;
import com.example.eventhub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedData(UserRepository userRepository, EventRepository eventRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .password("password")
                        .role(Role.ADMIN)
                        .build();
                User user = User.builder()
                        .username("user")
                        .email("user@example.com")
                        .password("password")
                        .role(Role.USER)
                        .build();
                userRepository.saveAll(List.of(admin, user));

                if (eventRepository.count() == 0) {
                    List<Event> events = new ArrayList<>();
                    events.add(Event.builder()
                            .title("Spring Music Festival")
                            .description("An outdoor music festival featuring various artists.")
                            .location("Central Park")
                            .eventDate(LocalDateTime.now().plusMonths(1))
                            .category("Music")
                            .price(new BigDecimal("49.99"))
                            .totalSeats(500)
                            .availableSeats(500)
                            .imageUrl("https://example.com/images/music1.jpg")
                            .createdBy(admin)
                            .build());
                    events.add(Event.builder()
                            .title("Art Expo 2026")
                            .description("Contemporary art exhibition with galleries from around the world.")
                            .location("Downtown Gallery")
                            .eventDate(LocalDateTime.now().plusWeeks(3))
                            .category("Art")
                            .price(new BigDecimal("25.00"))
                            .totalSeats(300)
                            .availableSeats(300)
                            .imageUrl("https://example.com/images/art1.jpg")
                            .createdBy(admin)
                            .build());
                    events.add(Event.builder()
                            .title("Tech Conference")
                            .description("Annual conference discussing the latest in technology.")
                            .location("Convention Center")
                            .eventDate(LocalDateTime.now().plusMonths(2))
                            .category("Technology")
                            .price(new BigDecimal("199.00"))
                            .totalSeats(1000)
                            .availableSeats(1000)
                            .imageUrl("https://example.com/images/tech1.jpg")
                            .createdBy(admin)
                            .build());
                    events.add(Event.builder()
                            .title("Food Truck Fiesta")
                            .description("A day of food trucks offering cuisines from around the world.")
                            .location("Beachside Boulevard")
                            .eventDate(LocalDateTime.now().plusWeeks(2))
                            .category("Food")
                            .price(new BigDecimal("10.00"))
                            .totalSeats(200)
                            .availableSeats(200)
                            .imageUrl("https://example.com/images/food1.jpg")
                            .createdBy(admin)
                            .build());
                    events.add(Event.builder()
                            .title("Yoga Retreat")
                            .description("A weekend yoga retreat in the mountains.")
                            .location("Mountain Resort")
                            .eventDate(LocalDateTime.now().plusMonths(1).plusDays(10))
                            .category("Wellness")
                            .price(new BigDecimal("150.00"))
                            .totalSeats(80)
                            .availableSeats(80)
                            .imageUrl("https://example.com/images/yoga1.jpg")
                            .createdBy(admin)
                            .build());
                    events.add(Event.builder()
                            .title("Charity Run")
                            .description("5K run to raise funds for local charities.")
                            .location("Riverside Park")
                            .eventDate(LocalDateTime.now().plusWeeks(4))
                            .category("Sports")
                            .price(new BigDecimal("20.00"))
                            .totalSeats(250)
                            .availableSeats(250)
                            .imageUrl("https://example.com/images/run1.jpg")
                            .createdBy(admin)
                            .build());
                    events.add(Event.builder()
                            .title("Comedy Night")
                            .description("Stand-up comedy featuring top comedians.")
                            .location("City Theater")
                            .eventDate(LocalDateTime.now().plusMonths(1).plusWeeks(1))
                            .category("Entertainment")
                            .price(new BigDecimal("35.00"))
                            .totalSeats(400)
                            .availableSeats(400)
                            .imageUrl("https://example.com/images/comedy1.jpg")
                            .createdBy(admin)
                            .build());
                    events.add(Event.builder()
                            .title("Historical Walking Tour")
                            .description("Guided tour of historical landmarks.")
                            .location("Old Town")
                            .eventDate(LocalDateTime.now().plusWeeks(5))
                            .category("Tour")
                            .price(new BigDecimal("15.00"))
                            .totalSeats(150)
                            .availableSeats(150)
                            .imageUrl("https://example.com/images/tour1.jpg")
                            .createdBy(admin)
                            .build());
                    eventRepository.saveAll(events);
                }
            }
        };
    }
}
