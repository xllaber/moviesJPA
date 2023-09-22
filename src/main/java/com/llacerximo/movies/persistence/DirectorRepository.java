package com.llacerximo.movies.persistence;

import com.llacerximo.movies.domain.entity.Director;

public interface DirectorRepository {
    Integer insert(Director director);
}
