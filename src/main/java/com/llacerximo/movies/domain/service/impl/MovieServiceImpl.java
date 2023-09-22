package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> getAll() {
        return movieRepository.getAll();
    }

    @Override
    public Movie findById(int id) {
        Movie movie = movieRepository.findById(id);
        if(movie == null) {
            throw new ResourceNotFoundException("Movie not found: " + id);
        }
        return movie;
    }
}
