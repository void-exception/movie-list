//package com.example.movielist.service;
//
//import com.example.movielist.model.Movie;
//import com.example.movielist.model.User;
//import com.example.movielist.repository.MovieRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class MovieService {
//
//    private final MovieRepository movieRepository;
//
//    public MovieService(MovieRepository movieRepository) {
//        this.movieRepository = movieRepository;
//    }
//
//    public List<Movie> findAllByUser(User user) {
//        return movieRepository.findByUserOrderByReleaseYearDesc(user);
//    }
//
//    public List<Movie> findByUserAndGenre(User user, String genre) {
//        if (genre == null || genre.isBlank() || "Все".equals(genre)) {
//            return findAllByUser(user);
//        }
//        return movieRepository.findByUserAndGenreOrderByReleaseYearDesc(user, genre);
//    }
//
//    public Movie findByIdAndUser(Long id, User user) {
//        return movieRepository.findByIdAndUser(id, user)
//                .orElseThrow(() -> new IllegalArgumentException("Фильм не найден"));
//    }
//
//    @Transactional
//    public Movie save(Movie movie, User user) {
//        movie.setUser(user);
//        return movieRepository.save(movie);
//    }
//
//    @Transactional
//    public Movie update(Long id, Movie updated, User user) {
//        Movie movie = findByIdAndUser(id, user);
//        movie.setTitle(updated.getTitle());
//        movie.setReleaseYear(updated.getReleaseYear());
//        movie.setDirector(updated.getDirector());
//        movie.setGenre(updated.getGenre());
//        return movieRepository.save(movie);
//    }
//
//    @Transactional
//    public void delete(Long id, User user) {
//        Movie movie = findByIdAndUser(id, user);
//        movieRepository.delete(movie);
//    }
//}
