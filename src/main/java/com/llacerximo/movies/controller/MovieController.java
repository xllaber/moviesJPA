package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.http_response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final Integer LIMIT = 10;

    @Autowired
    MovieService movieService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Response getAll(@RequestParam Optional<Integer> page) {
//            System.out.println(movieService.getAll());
            return new Response(movieService.getAllPaginated(page), movieService.getTotalRecords(), page, LIMIT);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Movie find(@PathVariable("id") int id) {
//        System.out.println(movieService.findById(id));
        return movieService.findById(id);
    }

}
