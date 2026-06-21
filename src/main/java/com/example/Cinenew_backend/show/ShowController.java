package com.example.Cinenew_backend.show;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Cinenew_backend.show.dto.ShowRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shows")
public class ShowController {
    
    private final ShowService showService;

    public ShowController(ShowService showService){
        this.showService=showService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addShow")
    public ResponseEntity<Object> addShows(@RequestParam(required = true) String email,@Valid @RequestBody ShowRequestDTO showRequestDTO){
        String resp=showService.addShows(email,showRequestDTO);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getShows/{movieId}")
    public ResponseEntity<Object> getShows(@PathVariable Long movieId){
        return ResponseEntity.ok(showService.getShows(movieId));
    }

    @GetMapping("/getShowScreen/{showId}")
    public ResponseEntity<Object> getShowScreeen(@PathVariable Long showId){
        return ResponseEntity.ok(showService.getShowScreen(showId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("cancelShow/{showId}")
    public ResponseEntity<Object> deleteShow(@PathVariable Long showId){
        return ResponseEntity.ok(showService.cancelShow(showId));
    }
}
