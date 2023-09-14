package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll();
    Movie findById(int id);

}
