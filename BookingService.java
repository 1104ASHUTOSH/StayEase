package com.example.stayease.service;

import com.example.stayease.entity.Booking;
import com.example.stayease.entity.Hotel;
import com.example.stayease.entity.User;
import com.example.stayease.repository.BookingRepository;
import com.example.stayease.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public Booking bookRoom(User user, Hotel hotel) throws Exception {
        if (hotel.getAvailableRooms() <= 0) {
            throw new Exception("No rooms available");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setBookingDate(LocalDateTime.now());

        hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
        hotelRepository.save(hotel);

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            Hotel hotel = booking.getHotel();
            hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
            hotelRepository.save(hotel);
            bookingRepository.delete(booking);
        }
    }
}
