package com.llacerximo.movies.domain.repository;

import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.entity.MovieCharacter;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> getAllPaginated(Integer page, Integer pageSize);
    Integer getTotalRecords();
    Optional<Movie> findById(Integer id);
    Integer insert(Movie movie);
    void update(Movie movie);
    void delete(Integer id);
    void addCharacterToMovie(Integer movieId, MovieCharacter movieCharacter);
    void updateCharacterOfMovie(MovieCharacter movieCharacter, Integer movieId);
    void deleteCharacterOfMovie(Integer characterId, Integer movieId);
}
