package com.example.Cinenew_backend.booking;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.Cinenew_backend.showseat.ShowSeatRepository;

@Service
public class SeatCleanupScheduler {
    
    private final ShowSeatRepository showSeatRepository;

    public SeatCleanupScheduler(ShowSeatRepository showSeatRepository){
        this.showSeatRepository=showSeatRepository;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void releaseSeats(){
        showSeatRepository.releaseSeats();
    }
}
