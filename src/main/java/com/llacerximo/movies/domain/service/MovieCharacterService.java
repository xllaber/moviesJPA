package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.MovieCharacter;

public interface MovieCharacterService {

    MovieCharacter getById(Integer id);
    MovieCharacter getByMovieId(Integer id);

}
