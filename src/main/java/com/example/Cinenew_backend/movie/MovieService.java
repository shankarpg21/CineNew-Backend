package com.example.Cinenew_backend.movie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Cinenew_backend.exception.UnauthorizedException;
import com.example.Cinenew_backend.show.ShowRepository;
import com.example.Cinenew_backend.user.User;
import com.example.Cinenew_backend.user.UserRepository;
import com.example.Cinenew_backend.enumData.Role;

@Service
public class MovieService {
    
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;

    public MovieService(UserRepository userRepository,MovieRepository movieRepository,ShowRepository showRepository){
        this.userRepository=userRepository;
        this.showRepository=showRepository;
        this.movieRepository=movieRepository;
    }
    

    public String addMovies(String email,MovieRequestDTO movieRequestDTO){
        User user=userRepository.findByEmail(email);
        if(!user.getRole().equals(Role.ADMIN)) throw new UnauthorizedException("Only admin can able to add movie");
        Movie movie=new Movie();
        movie.setMovieName(movieRequestDTO.getMovieName());
        movie.setMovieDesc(movieRequestDTO.getMovieDesc());
        movie.setMovieUrl(movieRequestDTO.getMovieUrl());
        movieRepository.save(movie);
        return "Movie added successfully";
    }

    public List<MovieResponseDTO> getMovies(){
        List<MovieResponseDTO> upcomingShows=showRepository.findMovies(LocalDate.now(),LocalTime.now());
        return upcomingShows;
    }
}
