package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllPaginated(Integer page, Integer pageSize);
    List<Movie> getAll();
    Movie findById(int id);
    Integer getTotalRecords();
}
