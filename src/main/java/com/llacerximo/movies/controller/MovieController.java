package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.http_response.Response;
import com.llacerximo.movies.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Response getAll(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return new Response(movieService.getAllPaginated(page, pageSize), new PaginationUtils(movieService.getTotalRecords(), pageSize, page));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response find(@PathVariable("id") int id) {
        return new Response(movieService.findById(id));
    }


}
