package com.llacerximo.movies.controller;

import com.llacerximo.movies.controller.model.MovieCharacter.MovieCharacterCreateWeb;
import com.llacerximo.movies.controller.model.MovieCharacter.MovieCharacterUpdateWeb;
import com.llacerximo.movies.controller.model.actor.ActorCreateWeb;
import com.llacerximo.movies.controller.model.movie.MovieCreateWeb;
import com.llacerximo.movies.controller.model.movie.MovieDetailWeb;
import com.llacerximo.movies.controller.model.movie.MovieListWeb;
import com.llacerximo.movies.controller.model.movie.MovieUpdateWeb;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.entity.MovieCharacter;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.http_response.Response;
import com.llacerximo.movies.mapper.MovieCharacterMapper;
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
    public Response getAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<Movie> movies = movieService.getAllPaginated(page, pageSize);
        List<MovieListWeb> movieWeb = movies.stream()
                .map(MovieMapper.mapper::toMovieListWeb)
                .toList();
        PaginationUtils pagination = PaginationUtils.builder()
                .page(page)
                .pageSize(pageSize)
                .totalRecords(movieService.getTotalRecords())
                .build();
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
                movieCreateWeb.getCharacters()
        );
        MovieListWeb movieListWeb = new MovieListWeb();
        movieListWeb.setTitle(movieCreateWeb.getTitle());
        movieListWeb.setId(id);
        return new Response(movieListWeb);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody MovieUpdateWeb movieUpdateWeb) {
        movieUpdateWeb.setId(id);
        movieService.update(MovieMapper.mapper.toMovie(movieUpdateWeb), movieUpdateWeb.getDirectorId(), movieUpdateWeb.getCharacterIds());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        movieService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{movieId}/characters")
    public Response addCharacterToMovie(@PathVariable("movieId") Integer movieId, @RequestBody MovieCharacterCreateWeb movieCharacterCreateWeb) {
        movieService.addCharacterToMovie(movieCharacterCreateWeb.getActorId(),
                movieId,
                MovieCharacterMapper.mapper.toMovieCharacter(movieCharacterCreateWeb)
        );
        return new Response(MovieMapper.mapper.toMovieDetailWeb(movieService.findById(movieId)));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{movieId}/characters/{characterId}")
    public Response updateCharacterOfMovie(@PathVariable("movieId") Integer movieId, @PathVariable("characterId") Integer characterId, @RequestBody MovieCharacterUpdateWeb movieCharacterUpdateWeb) {
        movieService.updateCharacterOfMovie(
                MovieCharacterMapper.mapper.toMovieCharacter(movieCharacterUpdateWeb),
                characterId,
                movieCharacterUpdateWeb.getActorId(),
                movieId
        );
        return new Response(MovieMapper.mapper.toMovieDetailWeb(movieService.findById(movieId)));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{movieId}/characters/{characterId}")
    public void deleteCharacter(@PathVariable("movieId") Integer movieId, @PathVariable("characterId") Integer characterId) {
        movieService.deleteCharacterOfMovie(characterId, movieId);
    }
    
}
