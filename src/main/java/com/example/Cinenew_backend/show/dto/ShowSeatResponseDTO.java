package com.example.Cinenew_backend.show.dto;

import java.time.LocalDateTime;

import com.example.Cinenew_backend.enumData.SeatStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatResponseDTO {
    
    private Long seatId;

    private String seatName;

    private SeatStatus seatStatus;

    private LocalDateTime holdExpiry;
}
