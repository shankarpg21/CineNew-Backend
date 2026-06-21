package com.example.Cinenew_backend.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    
    private final MovieService movieService;
    
    public MovieController(MovieService movieService){
        this.movieService=movieService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addMovie")
    public ResponseEntity<Object> addMovie(@RequestParam(required = true) String email,@Valid @RequestBody MovieRequestDTO movieRequestDTO){
        String resp=movieService.addMovies(email, movieRequestDTO);
        return ResponseEntity.ok(resp);
    }
    
    @GetMapping("/getScheduledMovies")
    public ResponseEntity<Object> getMovies(){
        return ResponseEntity.ok(movieService.getMovies());
    }
}
