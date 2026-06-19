package com.example.Cinenew_backend.movie;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDTO {
   

    @NotBlank(message = "Moviename is missing")
    private String movieName;

    @NotBlank(message = "Movie description is mandatory")
    private String movieDesc;

    @NotBlank(message = "Movie url is missing")
    private String movieUrl;
}
