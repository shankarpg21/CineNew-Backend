package com.example.Cinenew_backend.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>{

    @Query(value = "select * from booking where status='CONFIRMED' and booking_id=:bookingId",nativeQuery = true)
    public Booking findValidBooking(@Param("bookingId") Long bookingId);
  
    @Transactional
    @Modifying
    @Query(value="update booking set status='CANCELLED',cancelled_by='ADMIN',cancelled_at=NOW() where show_id=:showId and status='CONFIRMED'",nativeQuery = true)
    public int cancelBookings(@Param("showId") Long showId);

    @Query(value="select b.bookingId,b.status,b.bookedAt,b.cancelledAt,b.cancelledBy,s.seatName,m.movieName,sh.showId,sh.showDate,sh.showTime,sc.screenName from Booking b join b.seatName ss join ss.seat s join b.show sh join sh.screen sc join sh.movie m")
    public List<?> getUserBookings(@Param("userId") Long userId);
} 