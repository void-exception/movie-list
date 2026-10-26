package com.example.movielist.repository;


import com.example.movielist.model.Genre;
import com.example.movielist.model.Movie;
import com.example.movielist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByUserOrderByYearDesc(User user);
    List<Movie> findByUserOrderByYearAsc(User user);
    List<Movie> findByUserAndGenreOrderByYearDesc(User user, Genre genre);
    List<Movie> findByUserAndGenreOrderByYearAsc(User user, Genre genre);
}
