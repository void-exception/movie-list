package com.example.movielist.controller;


import com.example.movielist.model.Genre;
import com.example.movielist.model.Movie;
import com.example.movielist.model.User;
import com.example.movielist.repository.MovieRepository;
import com.example.movielist.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public MovieController(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    private User currentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userId == null ? null : userRepository.findById(userId).orElse(null);
    }

    @GetMapping
    public String list(@RequestParam(required = false) String genre,
                       @RequestParam(defaultValue = "desc") String sort,
                       HttpSession session, Model model) {
        User user = currentUser(session);
        if (user == null) return "redirect:/login";

        Genre genreFilter = null;
        if (genre != null && !genre.isBlank()) {
            try { genreFilter = Genre.valueOf(genre); } catch (IllegalArgumentException ignored) {}
        }

        boolean asc = "asc".equals(sort);
        List<Movie> movies;
        if (genreFilter == null) {
            movies = asc ? movieRepository.findByUserOrderByYearAsc(user)
                    : movieRepository.findByUserOrderByYearDesc(user);
        } else {
            movies = asc ? movieRepository.findByUserAndGenreOrderByYearAsc(user, genreFilter)
                    : movieRepository.findByUserAndGenreOrderByYearDesc(user, genreFilter);
        }

        model.addAttribute("movies", movies);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("selectedGenre", genre == null ? "" : genre);
        model.addAttribute("sort", sort);
        model.addAttribute("login", user.getLogin());
        return "movies";
    }

    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (currentUser(session) == null) return "redirect:/login";
        model.addAttribute("genres", Genre.values());
        return "add-movie";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,
                      @RequestParam Integer year,
                      @RequestParam String director,
                      @RequestParam String genre,
                      HttpSession session) {
        User user = currentUser(session);
        if (user == null) return "redirect:/login";

        Movie movie = new Movie();
        movie.setUser(user);
        movie.setTitle(title);
        movie.setYear(year);
        movie.setDirector(director);
        movie.setGenre(Genre.valueOf(genre));
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        User user = currentUser(session);
        if (user == null) return "redirect:/login";
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null || !movie.getUser().getId().equals(user.getId())) return "redirect:/movies";

        model.addAttribute("movie", movie);
        model.addAttribute("genres", Genre.values());
        return "edit-movie";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam Integer year,
                       @RequestParam String director,
                       @RequestParam String genre,
                       HttpSession session) {
        User user = currentUser(session);
        if (user == null) return "redirect:/login";
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null || !movie.getUser().getId().equals(user.getId())) return "redirect:/movies";

        movie.setTitle(title);
        movie.setYear(year);
        movie.setDirector(director);
        movie.setGenre(Genre.valueOf(genre));
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = currentUser(session);
        if (user == null) return "redirect:/login";
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null && movie.getUser().getId().equals(user.getId())) {
            movieRepository.delete(movie);
        }
        return "redirect:/movies";
    }
}