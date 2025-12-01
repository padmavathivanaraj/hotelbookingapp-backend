package com.example.hotelbooking_backend.repository;
import com.example.hotelbooking_backend.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCityContainingIgnoreCase(String city);
}
