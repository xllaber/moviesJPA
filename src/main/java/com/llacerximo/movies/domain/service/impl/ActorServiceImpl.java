package com.llacerximo.movies.domain.service.impl;

import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.service.ActorService;
import com.llacerximo.movies.persistence.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Override
    public Integer insert(Actor actor) {
        Integer id = actorRepository.insert(actor);
        return id;
    }
}
