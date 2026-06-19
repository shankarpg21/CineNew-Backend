package com.example.Cinenew_backend.screen;

import java.util.List;

import com.example.Cinenew_backend.seat.Seat;
import com.example.Cinenew_backend.show.Show;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Screen {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long screenId;

    @Column(nullable = false,unique = true)
    private String screenName;

    @OneToMany(mappedBy = "screen",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    List<Seat> seats;

    @OneToMany(mappedBy = "screen",fetch = FetchType.LAZY)
    List<Show> shows;
}
