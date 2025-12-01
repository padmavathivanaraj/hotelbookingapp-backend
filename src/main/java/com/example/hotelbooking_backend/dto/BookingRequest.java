package com.example.hotelbooking_backend.dto;
import lombok.Data;
import java.time.LocalDate;
@Data
public class BookingRequest {
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
