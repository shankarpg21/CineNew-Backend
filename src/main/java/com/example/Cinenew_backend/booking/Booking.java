package com.example.Cinenew_backend.booking;

import java.time.LocalDateTime;
import java.util.List;

import com.example.Cinenew_backend.enumData.CancelledBy;
import com.example.Cinenew_backend.enumData.TicketStatus;
import com.example.Cinenew_backend.show.Show;
import com.example.Cinenew_backend.showseat.ShowSeat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    private Show show;


    @OneToMany(mappedBy = "booking")
    private List<ShowSeat> seatName;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    @Enumerated(EnumType.STRING)
    private CancelledBy cancelledBy;
}
