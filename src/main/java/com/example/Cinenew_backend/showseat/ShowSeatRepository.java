package com.example.Cinenew_backend.showseat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Cinenew_backend.show.dto.ShowSeatResponseDTO;

import jakarta.transaction.Transactional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long>{

    @Query("select new com.example.Cinenew_backend.show.dto.ShowSeatResponseDTO(seat.seatId,seat.seatName,seatStatus,holdExpiry) from ShowSeat where show.showId=:showId order by seat.seatId")
    public List<ShowSeatResponseDTO> findScreenByShowId(@Param("showId") Long showId);

    @Query(value = "select * from show_seat where show_id=:showId and seat_id in (:seatIds)",nativeQuery = true)
    public List<ShowSeat> findByShowSeatId(@Param("showId") Long showId,@Param("seatIds") List<Long> seatIds);

    @Query(value="select s.seat_name from show_seat ss join seat s on ss.seat_id=s.seat_id where ss.booking_id=:bookingId",nativeQuery = true)
    public List<String> findSeatsByBookings(@Param("bookingId") Long bookingId);
    
    @Transactional
    @Modifying
    @Query(value = "update show_seat set seat_status='LOCKED', hold_expiry=:holdExpiry where seat_id in (:showSeats) and show_id=:showId and seat_status='AVAILABLE'",nativeQuery=true)
    public int updateSeats(@Param("showId") Long showId,@Param("showSeats") List<Long> showSeats,@Param("holdExpiry") LocalDateTime holdExpiry);

    @Transactional
    @Modifying
    @Query(value = "update show_seat set seat_status='AVAILABLE',hold_expiry=null where hold_expiry<NOW()",nativeQuery = true)
    public int releaseSeats();

    @Transactional
    @Modifying
    @Query(value = "update show_seat set seat_status='AVAILABLE',booking_id=null,user_id=null where booking_id=:bookingId and seat_status='BOOKED'",nativeQuery = true)
    public int cancelSeatsByBookingId(@Param("bookingId") Long bookingId);

    @Transactional
    @Modifying
    @Query(value = "update show_seat set seat_status='AVAILABLE',booking_id=null where show_id=:showId",nativeQuery = true)
    public int cancelSeatsByShowId(@Param("showId") Long showId);
}