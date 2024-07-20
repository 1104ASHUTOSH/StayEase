package com.example.stayease.controller;

import com.example.stayease.entity.Hotel;
import com.example.stayease.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.saveHotel(hotel);
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        return hotelService.getHotelById(id)
                .map(existingHotel -> {
                    existingHotel.setName(hotel.getName());
                    existingHotel.setLocation(hotel.getLocation());
                    existingHotel.setDescription(hotel.getDescription());
                    existingHotel.setAvailableRooms(hotel.getAvailableRooms());
                    return ResponseEntity.ok(hotelService.updateHotel(existingHotel));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize
