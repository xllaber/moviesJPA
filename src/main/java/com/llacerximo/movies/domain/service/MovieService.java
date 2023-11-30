package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.controller.model.MovieCharacter.MovieCharacterUpdateWeb;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.entity.MovieCharacter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllPaginated(Integer page, Integer pageSize);
    Movie findById(Integer id);
    Integer getTotalRecords();
    Integer insert(Movie movie, Integer directorId, Map<Integer, String> actorIds);
    void update(Movie movie, Integer directorId, List<Integer> characterIds);
    void delete(Integer id);
    void addCharacterToMovie(Integer actorId, Integer movieId, MovieCharacter movieCharacter);
    void updateCharacterOfMovie(MovieCharacter movieCharacter, Integer characterId, Integer actorId, Integer movieId);
    void deleteCharacterOfMovie(Integer characterId, Integer movieId);
}
