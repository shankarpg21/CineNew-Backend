package com.example.Cinenew_backend.show;


import java.time.LocalDate;
import java.time.LocalTime;

import com.example.Cinenew_backend.enumData.ShowStatus;
import com.example.Cinenew_backend.movie.Movie;
import com.example.Cinenew_backend.screen.Screen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shows",indexes = {@Index(name="movie_show",columnList = "show_date,show_time")})
public class Show {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long showId;

    @Column(nullable = false)
    private LocalDate showDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="screen_id",nullable = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalTime showTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id",nullable = false)
    private Movie movie;

    @Enumerated(EnumType.STRING)
    private ShowStatus status;

}
