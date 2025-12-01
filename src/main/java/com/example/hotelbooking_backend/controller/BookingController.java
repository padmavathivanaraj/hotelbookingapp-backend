package com.example.hotelbooking_backend.controller;

import com.example.hotelbooking_backend.dto.BookingRequest;
import com.example.hotelbooking_backend.entity.Booking;
import com.example.hotelbooking_backend.entity.User;
import com.example.hotelbooking_backend.repository.UserRepository;
import com.example.hotelbooking_backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private UserRepository userRepo;

    @PostMapping
    public Booking book(@RequestBody BookingRequest req, Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        return bookingService.book(user.getId(), req.getRoomId(), req.getCheckIn(), req.getCheckOut());
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        bookingService.cancel(id);
        return "cancelled";
    }
}
