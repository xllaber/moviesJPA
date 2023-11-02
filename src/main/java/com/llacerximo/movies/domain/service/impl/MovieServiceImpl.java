package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.domain.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> getAllPaginated(Integer page, Integer pageSize) {
        return movieRepository.getAllPaginated(page, pageSize);
    }

    @Override
    public Movie findById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la pelicula con id: " + id));
    }

    public Integer getTotalRecords(){
        return movieRepository.getTotalRecords();
    }
}
