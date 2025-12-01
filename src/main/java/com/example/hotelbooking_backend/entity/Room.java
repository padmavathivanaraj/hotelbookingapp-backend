package com.example.hotelbooking_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    private String type; // SINGLE, DOUBLE, SUITE, etc.

    private Double pricePerNight;

    @Builder.Default
    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
