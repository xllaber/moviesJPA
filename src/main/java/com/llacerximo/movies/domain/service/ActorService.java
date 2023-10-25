package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<Actor> getAll();

    List<Actor> getAllPaginated(Integer page, Integer pageSizeInput);

    Actor getById(Integer id);

    Integer getTotalRecords();

    Integer insert(Actor actor);

    void update(Actor actor);

    void delete(Integer id);
}
