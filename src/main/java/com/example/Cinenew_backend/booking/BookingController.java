package com.example.Cinenew_backend.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService=bookingService;
    }

    @PostMapping("/bookShows")
    public ResponseEntity<Object> bookShows(@Valid @RequestBody BookingRequestDTO bookingRequestDTO){
        return ResponseEntity.ok(bookingService.bookShows(bookingRequestDTO));
    } 

    @DeleteMapping("/cancelTicket/{bookingId}")
    public ResponseEntity<Object> cancelTicket(@PathVariable Long bookingId){
        return ResponseEntity.ok(bookingService.cancelTicket(bookingId));
    }

    @GetMapping("/getUserBookings/{userId}")
    public ResponseEntity<Object> getBookingsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }
}

