package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.repository.ActorRepository;
import com.llacerximo.movies.domain.repository.DirectorRepository;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.domain.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Movie> getAllPaginated(Integer page, Integer pageSize) {
        return movieRepository.getAllPaginated(page, pageSize);
    }

    @Override
    public Movie findById(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la pelicula con id: " + id));
        movie.setDirector(
                directorRepository.getByMovieId(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el director de la pelicula con id: " + id))
        );
        List<Actor> actors = actorRepository.getByMovieId(id);
//        movie.setActors(actors);
        return movie;
    }

    public Integer getTotalRecords(){
        return movieRepository.getTotalRecords();
    }

    @Override
    public Integer insert(Movie movie, Integer directorId, List<Integer> actorIds) {
        Director director = directorRepository.getById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el director con id " + directorId));
        List<Actor> actors = actorIds.stream()
                .map(actorId -> actorRepository.getById(actorId)
                        .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el actor con id " + actorId))
                )
                .toList();
        movie.setDirector(director);
        movie.setActors(actors);
        return movieRepository.insert(movie);
    }

    @Override
    public void update(Movie movie) {
        movieRepository.findById(movie.getId()).orElseThrow(() -> new ResourceNotFoundException("no se ha encontrado la pelicula con id " + movie.getId()));
        movieRepository.update(movie);
    }

    @Override
    public void delete(Integer id) {
        movieRepository.findById(id).orElseThrow(()  -> new ResourceNotFoundException("No se ha encontrado la pelicula con id: " + id));
        movieRepository.delete(id);
    }

}
