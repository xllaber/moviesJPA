package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<Movie> getAll() {
        try {
            System.out.println(movieService.getAll());
            return movieService.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Movie find(@PathVariable("id") int id) {
        try {
            System.out.println(movieService.findById(id));
            return movieService.findById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

}
