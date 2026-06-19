package com.example.Cinenew_backend.booking;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.Cinenew_backend.enumData.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    
    private TicketStatus status;
    
    private Long bookingId;
    
    private String movieName;

    private String screenName;

    private List<String> bookedSeats;

    private LocalDate showDate;

    private LocalTime showTime;

}
