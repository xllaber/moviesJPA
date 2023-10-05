package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.DirectorService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.persistence.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    DirectorRepository directorRepository;

    @Override
    public List<Director> getAllPaginated(Optional<Integer> page, Integer pageSizeInput) {
        return directorRepository.getAllPaginated(page, pageSizeInput);
    }

    @Override
    public List<Director> getAll() {
        return directorRepository.getAll();
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
        Director existingDirector = director;
        if (existingDirector == null){
            throw new ResourceNotFoundException("Director not found: " + director.getId());
        }
        directorRepository.update(director);
    }

    @Override
    public void delete(Integer id) {
        Director existingDirector = directorRepository.getById(id);
        if (existingDirector == null){
            throw new ResourceNotFoundException("Director not found");
        }
        directorRepository.delete(id);
    }
}
