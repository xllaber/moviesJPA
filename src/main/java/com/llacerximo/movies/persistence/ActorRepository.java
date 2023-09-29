package com.llacerximo.movies.persistence;

import com.llacerximo.movies.domain.entity.Actor;

public interface ActorRepository {
    Integer insert(Actor actor);
}
