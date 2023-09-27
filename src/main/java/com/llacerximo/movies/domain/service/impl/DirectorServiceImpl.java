package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.DirectorService;
import com.llacerximo.movies.persistence.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;

    @Override
    public Integer create(Director director) {
        return directorRepository.insert(director);
    }
}
