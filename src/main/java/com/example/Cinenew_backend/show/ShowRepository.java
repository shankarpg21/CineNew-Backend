package com.example.Cinenew_backend.show;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Cinenew_backend.movie.MovieResponseDTO;
import com.example.Cinenew_backend.show.dto.ShowResponseDTO;

import jakarta.transaction.Transactional;

@Repository
public interface ShowRepository extends JpaRepository<Show,Long>{

    @Query("select distinct new com.example.Cinenew_backend.movie.MovieResponseDTO(m.movieId,m.movieName,m.movieDesc,m.movieUrl) from Show s  join s.movie m where s.status='ACTIVE' and (s.showDate>:date or (s.showDate=:date and s.showTime>:time)) ")
    public List<MovieResponseDTO> findMovies(LocalDate date,LocalTime time);

    @Query("select new com.example.Cinenew_backend.show.dto.ShowResponseDTO(m.movieName,sc.screenName,s.showDate,s.showTime) from Show s join s.movie m join s.screen sc where m.movieId=:movieId and s.status='ACTIVE' and ((s.showDate=:date and s.showTime>:time) or s.showDate>:date)  order by s.showDate,s.showTime")
    public List<ShowResponseDTO> findShowByMovieId(@Param("movieId") Long movieId,@Param("date") LocalDate date,@Param("time") LocalTime time);

    @Transactional
    @Modifying
    @Query(value = "update shows set status='INACTIVE' where show_id=:showId",nativeQuery = true)
    public int cancelShow(@Param("showId") Long showId);

    @Query(value = "select * from shows where show_id=:showId and status='ACTIVE'",nativeQuery = true)
    public Show findShowByStatus(@Param("showId") Long showId);
} 
