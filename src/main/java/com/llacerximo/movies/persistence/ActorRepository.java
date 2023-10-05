package com.llacerximo.movies.persistence;

import com.llacerximo.movies.domain.entity.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorRepository {
    List<Actor> getAll();
    List<Actor>getAllPaginated(Optional<Integer> page, Integer pageSize);
    Actor getById(Integer id);

    Integer getTotalRecords();

    Integer insert(Actor actor);

    void update(Actor actor);

    void delete(Integer id);
}
