package com.example.Cinenew_backend.movie;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDTO {

    private Long movieId;
    
    private String movieName;

    private String movieDesc;

    private String movieUrl;

}
