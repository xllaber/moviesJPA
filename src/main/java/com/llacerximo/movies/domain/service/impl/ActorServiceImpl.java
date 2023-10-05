package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.ActorService;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.persistence.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Actor> getAll() {
        return actorRepository.getAll();
    }

    @Override
    public List<Actor> getAllPaginated(Optional<Integer> page, Integer pageSizeInput) {
        return actorRepository.getAllPaginated(page, pageSizeInput);
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
        Actor existingActor = actor;
        if (existingActor == null){
            throw new ResourceNotFoundException("Actor not found: " + actor.getId());
        }
        actorRepository.update(actor);
    }

    @Override
    public void delete(Integer id) {
        Actor existingActor = actorRepository.getById(id);
        if (existingActor == null){
            throw new ResourceNotFoundException("Director not found");
        }
        actorRepository.delete(id);
    }
}
