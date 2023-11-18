package com.llacerximo.movies.domain.repository;

import com.llacerximo.movies.domain.entity.MovieCharacter;

import java.util.Optional;

public interface MovieCharacterRepository {

    Optional<MovieCharacter> getById(Integer id);

}
