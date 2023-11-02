package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.DirectorService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.domain.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;

    @Override
    public List<Director> getAllPaginated(Integer page, Integer pageSizeInput) {
        return directorRepository.getAllPaginated(page, pageSizeInput);
    }

    @Override
    public Director getById(Integer id) {
        return directorRepository.getById(id).orElseThrow(() -> new ResourceNotFoundException("Director con id " + id + " mo encontrado"));
    }

    @Override
    public Integer getTotalRecords() {
        return directorRepository.getTotalRecords();
    }

    @Override
    public Integer insert(Director director) {
        return directorRepository.insert(director);
    }

    @Override
    public void update(Director director) {
        directorRepository.getById(director.getId()).orElseThrow(() -> new ResourceNotFoundException("Director with id " + director.getId() + "not found"));
        directorRepository.update(director);
    }

    @Override
    public void delete(Integer id) {
        directorRepository.getById(id).orElseThrow(() -> new ResourceNotFoundException("Director with id " + id + "not found"));
        directorRepository.delete(id);
    }
}
