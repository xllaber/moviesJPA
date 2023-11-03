package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllPaginated(Integer page, Integer pageSize);
    Movie findById(Integer id);
    Integer getTotalRecords();
    Integer insert(Movie movie, Integer directorId, List<Integer> actorIds);
    void update(Movie movie);
    void delete(Integer id);
}
