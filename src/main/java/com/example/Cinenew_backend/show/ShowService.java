package com.example.Cinenew_backend.show;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.Cinenew_backend.booking.BookingRepository;
import com.example.Cinenew_backend.enumData.Role;
import com.example.Cinenew_backend.enumData.ShowStatus;
import com.example.Cinenew_backend.exception.InvalidShowException;
import com.example.Cinenew_backend.exception.MovieNotAvailableException;
import com.example.Cinenew_backend.exception.ScreenNotAvailableException;
import com.example.Cinenew_backend.exception.UnauthorizedException;
import com.example.Cinenew_backend.movie.Movie;
import com.example.Cinenew_backend.movie.MovieRepository;
import com.example.Cinenew_backend.screen.Screen;
import com.example.Cinenew_backend.screen.ScreenRepository;
import com.example.Cinenew_backend.seat.Seat;
import com.example.Cinenew_backend.show.dto.ShowRequestDTO;
import com.example.Cinenew_backend.show.dto.ShowResponseDTO;
import com.example.Cinenew_backend.show.dto.ShowSeatLayOutDTO;
import com.example.Cinenew_backend.show.dto.ShowSeatResponseDTO;
import com.example.Cinenew_backend.showseat.ShowSeat;
import com.example.Cinenew_backend.showseat.ShowSeatRepository;
import com.example.Cinenew_backend.user.User;
import com.example.Cinenew_backend.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ShowService {
    
    private ShowRepository showRepository;
    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    private UserRepository userRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    public ShowService(ShowRepository showRepository,MovieRepository movieRepository,ScreenRepository screenRepository,UserRepository userRepository,ShowSeatRepository showSeatRepository,BookingRepository bookingRepository){
        this.showRepository=showRepository;
        this.userRepository=userRepository;
        this.movieRepository=movieRepository;
        this.screenRepository=screenRepository;
        this.showSeatRepository=showSeatRepository;
        this.bookingRepository=bookingRepository;
    }

    public String addShows(String email,ShowRequestDTO showRequestDTO){
        User user=userRepository.findByEmail(email);
        if(!user.getRole().equals(Role.ADMIN)) throw new UnauthorizedException("Only admin can able to add movie");
        Optional<Movie> movie=movieRepository.findById(showRequestDTO.getMovieId());
        if(!movie.isPresent()){
            throw new MovieNotAvailableException("Movie not present in database");
        }
        Optional<Screen> screen=screenRepository.findById(showRequestDTO.getScreenId());
        if(!screen.isPresent()){
            throw new ScreenNotAvailableException("Screen not present in database");
        }
        Show show=new Show();
        show.setMovie(movie.get());
        show.setScreen(screen.get());
        show.setStatus(ShowStatus.ACTIVE);
        show.setShowDate(showRequestDTO.getShowDate());
        show.setShowTime(showRequestDTO.getShowTime());
        showRepository.save(show);

        List<Seat> seats=screen.get().getSeats();
        for(Seat seat:seats){
            ShowSeat showSeat=new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeatRepository.save(showSeat);
        }
        return "Show added succesfully";
    }

    public List<ShowResponseDTO> getShows(Long movieId){
        Optional<Movie> movie=movieRepository.findById(movieId);
        if(!movie.isPresent()) throw new MovieNotAvailableException("Invalid movie id");
        List<ShowResponseDTO> upcomingShowsForMovie=showRepository.findShowByMovieId(movieId,LocalDate.now(),LocalTime.now());
        return upcomingShowsForMovie;
    }

    public ShowSeatLayOutDTO getShowScreen(Long showId){
        Show show=showRepository.findShowByStatus(showId);
        if(show==null) throw new InvalidShowException("Invalid show id");
        List<ShowSeatResponseDTO> showSeats=showSeatRepository.findScreenByShowId(showId);
        return new ShowSeatLayOutDTO(showId, showSeats);
    }

    @Transactional
    public String cancelShow(Long showId){
        Show show=showRepository.findShowByStatus(showId);
        if(show==null) throw new InvalidShowException("Invalid show id");
        showRepository.cancelShow(showId);
        showSeatRepository.cancelSeatsByShowId(showId);
        bookingRepository.cancelBookings(showId);
        return "Show cancelled successfully";
    }

}
