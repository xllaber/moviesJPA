package com.llacerximo.movies.domain.repository;

import com.llacerximo.movies.domain.entity.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorRepository {
//    List<Actor> getAll();
    List<Actor>getAllPaginated(Integer page, Integer pageSize);
    Optional<Actor> getById(Integer id);

    Integer getTotalRecords();

    Integer insert(Actor actor);

    void update(Actor actor);

    void delete(Integer id);

    Optional<Actor> getByMovieId(Integer movieId);
}
