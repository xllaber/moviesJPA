package com.llacerximo.movies.controller;

import com.llacerximo.movies.controller.model.actor.ActorCreateWeb;
import com.llacerximo.movies.controller.model.movie.MovieCreateWeb;
import com.llacerximo.movies.controller.model.movie.MovieDetailWeb;
import com.llacerximo.movies.controller.model.movie.MovieListWeb;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.http_response.Response;
import com.llacerximo.movies.mapper.MovieMapper;
import com.llacerximo.movies.utils.PaginationUtils;
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
    public Response getAll(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<Movie> movies = movieService.getAllPaginated(page, pageSize);
        List<MovieListWeb> movieWeb = movies.stream()
                .map(MovieMapper.mapper::toMovieListWeb)
                .toList();
        PaginationUtils pagination = PaginationUtils.builder()
                .page(page)
                .pageSize(pageSize)
                .totalRecords(movieService.getTotalRecords())
                .build();
        System.out.println(pagination.getTotalRecords());
        pagination.setNextAndPrevious(pagination.getTotalRecords(), page);
        return new Response(movieWeb, pagination);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response find(@PathVariable("id") Integer id) {
        MovieDetailWeb movieDetailWeb = MovieMapper.mapper.toMovieDetailWeb(movieService.findById(id));
        return new Response(movieDetailWeb);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Response insert(@RequestBody MovieCreateWeb movieCreateWeb) {
        Integer id = movieService.insert(
                MovieMapper.mapper.toMovie(movieCreateWeb),
                movieCreateWeb.getDirectorId(),
                movieCreateWeb.getActorIds()
        );
        MovieListWeb movieListWeb = new MovieListWeb();
        movieListWeb.setTitle(movieCreateWeb.getTitle());
        movieListWeb.setId(id);
        return new Response(movieListWeb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody MovieDetailWeb movieDetailWeb) {
        movieDetailWeb.setId(id);
        movieService.update(MovieMapper.mapper.toMovie(movieDetailWeb));
    }
}
