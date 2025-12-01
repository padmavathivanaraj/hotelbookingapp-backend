package com.example.hotelbooking_backend.service;

import com.example.hotelbooking_backend.entity.Booking;
import com.example.hotelbooking_backend.entity.Room;
import com.example.hotelbooking_backend.entity.User;
import com.example.hotelbooking_backend.repository.BookingRepository;
import com.example.hotelbooking_backend.repository.RoomRepository;
import com.example.hotelbooking_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoomRepository roomRepo;

    public Booking book(Long userId, Long roomId, java.time.LocalDate checkIn, java.time.LocalDate checkOut) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

        if (Boolean.FALSE.equals(room.getAvailable())) {
            throw new RuntimeException("Room is not available");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) {
            throw new RuntimeException("Invalid check-in/check-out dates");
        }

        double amount = nights * room.getPricePerNight();

        Booking b = Booking.builder()
                .user(user)
                .room(room)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .amount(amount)
                .status("CONFIRMED")
                .build();

        // mark room unavailable and save both
        room.setAvailable(false);
        roomRepo.save(room);

        return bookingRepo.save(b);
    }

    public void cancel(Long bookingId) {
        Booking b = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        b.setStatus("CANCELLED");
        Room room = b.getRoom();
        if (room != null) {
            room.setAvailable(true);
            roomRepo.save(room);
        }

        bookingRepo.save(b);
    }
}
