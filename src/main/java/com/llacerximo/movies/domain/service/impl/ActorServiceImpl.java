package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.ActorService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.domain.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Actor> getAll() {
        return actorRepository.getAll();
    }

    @Override
    public List<Actor> getAllPaginated(Integer page, Integer pageSizeInput) {
        return actorRepository.getAllPaginated(page, pageSizeInput);
    }

    @Override
    public Actor getById(Integer id) {
        return actorRepository.getById(id).orElseThrow(() -> new ResourceNotFoundException("Actor con id " + id + " no encontrado"));
    }

    @Override
    public Integer getTotalRecords() {
        return actorRepository.getTotalRecords();
    }

    @Override
    public Integer insert(Actor actor) {
        return actorRepository.insert(actor);
    }

    @Override
    public void update(Actor actor) {
        actorRepository.getById(actor.getId()).orElseThrow(() -> new ResourceNotFoundException("Actor with id " + actor.getId() + " not found"));
        actorRepository.update(actor);
    }

    @Override
    public void delete(Integer id) {
        actorRepository.getById(id).orElseThrow(() -> new ResourceNotFoundException("Actor with id " + id + " not found"));
        actorRepository.delete(id);
    }
}
