package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.controller.model.MovieCharacter.MovieCharacterUpdateWeb;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.entity.MovieCharacter;
import com.llacerximo.movies.domain.repository.ActorRepository;
import com.llacerximo.movies.domain.repository.DirectorRepository;
import com.llacerximo.movies.domain.repository.MovieCharacterRepository;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.domain.repository.MovieRepository;
import com.llacerximo.movies.persistence.DAO.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    MovieCharacterRepository movieCharacterRepository;


    @Override
    public List<Movie> getAllPaginated(Integer page, Integer pageSize) {
        List<Movie> movies = movieRepository.getAllPaginated(page, pageSize);
        return movies;
    }

    @Override
    public Movie findById(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la pelicula con id: " + id));
        return movie;
    }

    public Integer getTotalRecords(){
        return movieRepository.getTotalRecords();
    }

    @Override
    public Integer insert(Movie movie, Integer directorId, Map<Integer, String> characters) {
        Director director = directorRepository.getById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el director con id " + directorId));
        List<MovieCharacter> movieCharacters = new ArrayList<>();
        characters.forEach((actorId, characterName) -> {
            MovieCharacter movieCharacter = new MovieCharacter();
            movieCharacter.setCharacter(characterName);
            movieCharacter.setActor(
                    actorRepository.getById(actorId)
                            .orElseThrow(() -> new ResourceNotFoundException("Actor con id " + actorId + " no encontrado"))
            );
            movieCharacters.add(movieCharacter);
        });
        movie.setDirector(director);
        movie.setCharacters(movieCharacters);
        return movieRepository.insert(movie);
    }

    @Override
    public void update(Movie movie, Integer directorId, List<Integer> characterIds) {
        movieRepository.findById(movie.getId())
                .orElseThrow(() -> new ResourceNotFoundException("no se ha encontrado la pelicula con id " + movie.getId()));
        Director director = directorRepository.getById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("no se ha encontrado el director con id " + directorId));
        List<MovieCharacter> movieCharacters = characterIds.stream()
                        .map(characterId -> {
                                    MovieCharacter movieCharacter = movieCharacterRepository.getById(characterId)
                                            .orElseThrow(() -> new ResourceNotFoundException("no se ha encontrado el personaje con id " + characterId));
                                    movieCharacter.setActor(actorRepository.getByCharacterId(movieCharacter.getId())
                                            .orElseThrow(() -> new ResourceNotFoundException("no se ha encontrado el actor del personaje con id " + characterId))
                                    );
                                    return movieCharacter;
                                }
                        )
                        .toList();
        movie.setDirector(director);
        movie.setCharacters(movieCharacters);
        movieRepository.update(movie);
    }

    @Override
    public void delete(Integer id) {
        movieRepository.findById(id).orElseThrow(()  -> new ResourceNotFoundException("No se ha encontrado la pelicula con id: " + id));
        movieRepository.delete(id);
    }

    @Override
    public void addCharacterToMovie(Integer actorId, Integer movieId, MovieCharacter movieCharacter) {
        movieCharacter.setActor(
                actorRepository.getById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor con id " + actorId + " no encontrado"))
        );
        movieRepository.addCharacterToMovie(movieId, movieCharacter);
    }

    @Override
    public void updateCharacterOfMovie(MovieCharacter movieCharacter, Integer characterId, Integer actorId, Integer movieId) {
        movieCharacterRepository.getById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("Personaje con id " + movieCharacter.getId() + " no encontrado"));
        movieCharacter.setId(characterId);
        movieCharacter.setActor(
                actorRepository.getById(actorId).orElseThrow(() -> new ResourceNotFoundException("Actor con id " + actorId + " no encontrado"))
        );
        movieRepository.updateCharacterOfMovie(movieCharacter, movieId);
    }

    @Override
    public void deleteCharacterOfMovie(Integer characterId, Integer movieId) {
        movieCharacterRepository.getById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("Personaje con id " + characterId + " no encontrado"));
        movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la pelicula con id: " + movieId));

        movieRepository.deleteCharacterOfMovie(characterId, movieId);
    }

}
