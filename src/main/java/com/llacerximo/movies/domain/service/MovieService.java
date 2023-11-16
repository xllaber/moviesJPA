package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.Movie;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllPaginated(Integer page, Integer pageSize);
    Movie findById(Integer id);
    Integer getTotalRecords();
    Integer insert(Movie movie, Integer directorId, Map<Integer, String> actorIds);
    void update(Movie movie);
    void delete(Integer id);
}
