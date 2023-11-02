package com.llacerximo.movies.domain.repository;

import com.llacerximo.movies.domain.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
//    List<Movie> getAll();
    List<Movie> getAllPaginated(Integer page, Integer pageSize);
    Integer getTotalRecords();
    Optional<Movie> findById(Integer id);

    Integer insert(Movie movie);

    void update(Movie movie);
}
