package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllPaginated(Integer page, Integer pageSize);
    Movie findById(Integer id);
    Integer getTotalRecords();

}
