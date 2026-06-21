package com.example.Cinenew_backend.booking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Cinenew_backend.enumData.CancelledBy;
import com.example.Cinenew_backend.enumData.SeatStatus;
import com.example.Cinenew_backend.enumData.TicketStatus;
import com.example.Cinenew_backend.exception.CancelTicketException;
import com.example.Cinenew_backend.exception.InvalidSeatException;
import com.example.Cinenew_backend.exception.InvalidShowException;
import com.example.Cinenew_backend.exception.InvalidTicketException;
import com.example.Cinenew_backend.exception.SeatUnavailableException;
import com.example.Cinenew_backend.show.Show;
import com.example.Cinenew_backend.show.ShowRepository;
import com.example.Cinenew_backend.showseat.ShowSeat;
import com.example.Cinenew_backend.showseat.ShowSeatRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    private ShowRepository showRepository;
    private BookingRepository bookingRepository;
    private ShowSeatRepository showSeatRepository;

    public BookingService(ShowRepository showRepository,BookingRepository bookingRepository,ShowSeatRepository showSeatRepository){
        this.bookingRepository=bookingRepository;
        this.showSeatRepository=showSeatRepository;
        this.showRepository=showRepository;
    }

    @Transactional
    public BookingResponseDTO bookShows(BookingRequestDTO bookingRequestDTO){
        Optional<Show> show=showRepository.findById(bookingRequestDTO.getShowId());
        if(show.isEmpty()) throw new InvalidShowException("Invalid show id");
        Set<Long> uniqueSeats=new HashSet<>(bookingRequestDTO.getSeatDetails());
        if(uniqueSeats.size()!=bookingRequestDTO.getSeatDetails().size()) throw new InvalidSeatException("Duplicate seats not allowed");
        List<ShowSeat> seatDetails=showSeatRepository.findByShowSeatId(bookingRequestDTO.getShowId(),bookingRequestDTO.getSeatDetails());
        if(seatDetails.size()!=bookingRequestDTO.getSeatDetails().size()) throw new InvalidSeatException("Invalid seat ids are not allowed");
        LocalDateTime holdExpiry=LocalDateTime.now().plusMinutes(5);
        int bookingSeats=showSeatRepository.updateSeats(bookingRequestDTO.getShowId(),bookingRequestDTO.getSeatDetails(),holdExpiry);
        if(bookingSeats!=bookingRequestDTO.getSeatDetails().size()){
            throw new SeatUnavailableException("Some seats has been taken");
        }
        Booking booking=new Booking();
        booking.setShow(show.get());        
        for(ShowSeat showSeat:seatDetails){
            showSeat.setBooking(booking);
            showSeat.setHoldExpiry(null);
            showSeat.setSeatStatus(SeatStatus.BOOKED);
        }
        booking.setSeatName(seatDetails);
        booking.setBookedAt(LocalDateTime.now());
        booking.setStatus(TicketStatus.CONFIRMED);
        Long userId=(Long)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        booking.setUserId(userId);
        Booking ticket=bookingRepository.save(booking);
        BookingResponseDTO res=new BookingResponseDTO();
        res.setBookingId(ticket.getBookingId());
        res.setScreenName(ticket.getShow().getScreen().getScreenName());
        res.setMovieName(ticket.getShow().getMovie().getMovieName());
        res.setShowDate(ticket.getShow().getShowDate());
        res.setShowTime(ticket.getShow().getShowTime());
        res.setStatus(TicketStatus.CONFIRMED);
        res.setBookedSeats(ticket.getSeatName().stream().map(x->x.getSeat().getSeatName()).toList());
        return res;
    }

    @Transactional
    public String cancelTicket(Long bookingId){
        Booking booking=bookingRepository.findValidBooking(bookingId);
        if(booking==null) throw new InvalidTicketException("Invalid booking id");
        LocalDateTime showTime=LocalDateTime.of(booking.getShow().getShowDate(),booking.getShow().getShowTime());
        long hoursLeft=Duration.between(LocalDateTime.now(),showTime).toHours();
        if(hoursLeft<=24) throw new CancelTicketException("Sorry, tickets cannot be cancelled less than 24 hours");
        booking.setStatus(TicketStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancelledBy(CancelledBy.USER);
        showSeatRepository.cancelSeatsByBookingId(bookingId);
        return "Booking cancelled succesfully";
    }

    public List<?> getUserBookings(){
        Long userId=(Long)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Booking> bookings=bookingRepository.getUserBookings(userId);
        List<BookingResponseDTO> resp=new ArrayList<>();
        for(Booking b:bookings){
            BookingResponseDTO bookingResponseDTO=new BookingResponseDTO();
            List<String> seatDetails=showSeatRepository.findSeatsByBookings(b.getBookingId());
            bookingResponseDTO.setBookedSeats(seatDetails);
            bookingResponseDTO.setBookingId(b.getBookingId());
            bookingResponseDTO.setMovieName(b.getShow().getMovie().getMovieName());
            bookingResponseDTO.setScreenName(b.getShow().getScreen().getScreenName());
            bookingResponseDTO.setShowDate(b.getShow().getShowDate());
            bookingResponseDTO.setShowTime(b.getShow().getShowTime());
            bookingResponseDTO.setStatus(b.getStatus());
            resp.add(bookingResponseDTO);
        }
        return resp;
    }

    public List<BookingResponseDTO> getShowBookings(Long showId){
        Show show=showRepository.findById(showId).orElseThrow(()->new InvalidShowException("Invalid show id"));
        List<Booking> bookings=bookingRepository.getShowBookings(showId);
        List<BookingResponseDTO> resp=new ArrayList<>();
        for(Booking b:bookings){
            BookingResponseDTO bookingResponseDTO=new BookingResponseDTO();
            List<String> seatDetails=showSeatRepository.findSeatsByBookings(b.getBookingId());
            bookingResponseDTO.setBookedSeats(seatDetails);
            bookingResponseDTO.setBookingId(b.getBookingId());
            bookingResponseDTO.setMovieName(b.getShow().getMovie().getMovieName());
            bookingResponseDTO.setScreenName(b.getShow().getScreen().getScreenName());
            bookingResponseDTO.setShowDate(b.getShow().getShowDate());
            bookingResponseDTO.setShowTime(b.getShow().getShowTime());
            bookingResponseDTO.setStatus(b.getStatus());
            resp.add(bookingResponseDTO);
        }
        return resp;
    }
}
