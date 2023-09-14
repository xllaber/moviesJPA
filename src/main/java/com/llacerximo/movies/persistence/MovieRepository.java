package com.llacerximo.movies.persistence;

import com.llacerximo.movies.domain.entity.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> getAll();

    Movie findById(int id);
}
