package com.example.hotelbooking_backend.service;

import com.example.hotelbooking_backend.entity.Hotel;
import com.example.hotelbooking_backend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepo;

    public Hotel createHotel(Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    public Hotel getHotel(Long id) {
        return hotelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
    }

    public List<Hotel> getAll() {
        return hotelRepo.findAll();
    }

    public List<Hotel> searchByCity(String city) {
        return hotelRepo.findByCityContainingIgnoreCase(city);
    }

    public Hotel update(Hotel hotel) {
        if (hotel.getId() == null) {
            throw new RuntimeException("Hotel id required for update");
        }
        // ensure exists
        Hotel existing = hotelRepo.findById(hotel.getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotel.getId()));
        // copy fields as needed (simple replace here)
        existing.setName(hotel.getName());
        existing.setCity(hotel.getCity());
        existing.setAddress(hotel.getAddress());
        existing.setDescription(hotel.getDescription());
        existing.setRating(hotel.getRating());
        return hotelRepo.save(existing);
    }

    public void delete(Long id) {
        hotelRepo.deleteById(id);
    }
}
