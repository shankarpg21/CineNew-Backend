package com.example.Cinenew_backend.booking;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {
    
    
    @NotNull(message = "Show id is missing")
    private Long showId;

    @NotNull(message = "Show seat details are missing")
    private List<@NotNull(message = "Seat id cannot be null") @Positive(message = "Seat id should be valid") Long> seatDetails; 
}
