package com.example.Cinenew_backend.show.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowResponseDTO {
    
    private String movieName;

    private String screenName;

    private LocalDate showDate;
    
    private LocalTime showTime;
}
