package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

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
