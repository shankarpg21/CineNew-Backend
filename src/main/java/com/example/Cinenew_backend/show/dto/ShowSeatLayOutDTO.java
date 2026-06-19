package com.example.Cinenew_backend.show.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatLayOutDTO {
    
    private Long showId;

    private List<ShowSeatResponseDTO> showSeats;
}
