package com.example.Cinenew_backend.movie;

import java.util.List;

import com.example.Cinenew_backend.show.Show;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long movieId;
    
    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String movieDesc;

    @Column(nullable = false)
    private String movieUrl;

    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY)
    private List<Show> shows;
}