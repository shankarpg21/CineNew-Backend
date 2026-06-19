package com.example.Cinenew_backend.showseat;


import java.time.LocalDateTime;

import com.example.Cinenew_backend.booking.Booking;
import com.example.Cinenew_backend.enumData.SeatStatus;
import com.example.Cinenew_backend.seat.Seat;
import com.example.Cinenew_backend.show.Show;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"show_id", "seat_id"})
    }
)
public class ShowSeat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id",nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat seat;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus seatStatus=SeatStatus.AVAILABLE;
    
    private LocalDateTime holdExpiry;

    @ManyToOne
    @JoinColumn(name ="booking_id")
    private Booking booking;
}
