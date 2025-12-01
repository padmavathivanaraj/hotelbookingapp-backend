package com.example.hotelbooking_backend.controller;

import com.example.hotelbooking_backend.entity.Hotel;
import com.example.hotelbooking_backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    @Autowired private HotelService hotelService;

    @GetMapping
    public List<Hotel> list(@RequestParam(required=false) String city){
        if(city == null) return hotelService.getAll();
        return hotelService.searchByCity(city);
    }

    @PostMapping
    public Hotel create(@RequestBody Hotel hotel){ return hotelService.createHotel(hotel); }

    @GetMapping("/{id}")
    public Hotel get(@PathVariable Long id){ return hotelService.getHotel(id);
 }

    @PutMapping("/{id}")
    public Hotel update(@PathVariable Long id, @RequestBody Hotel hotel){
        hotel.setId(id);
        return hotelService.update(hotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ hotelService.delete(id); }
}
