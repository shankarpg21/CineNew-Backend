package com.example.Cinenew_backend.show.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowRequestDTO {
    
    @NotNull(message = "Movie id is missing")
    private Long movieId;

    @NotNull(message = "Screen id is missing")
    private Long screenId;

    @NotNull(message = "Show date is mandatory")
    private LocalDate showDate;

    @NotNull(message = "Show time is mandatory")
    private LocalTime showTime;
}
