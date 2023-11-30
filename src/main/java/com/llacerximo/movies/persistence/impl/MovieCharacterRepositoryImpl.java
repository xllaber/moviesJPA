package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.MovieCharacter;
import com.llacerximo.movies.domain.repository.MovieCharacterRepository;
import com.llacerximo.movies.mapper.MovieCharacterMapper;
import com.llacerximo.movies.persistence.DAO.MovieCharacterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.Optional;

@Repository
public class MovieCharacterRepositoryImpl implements MovieCharacterRepository {

    @Autowired
    MovieCharacterDAO movieCharacterDAO;

    @Override
    public Optional<MovieCharacter> getById(Integer id) {
        Connection connection = DBUtil.open(true);
        MovieCharacter movieCharacter = MovieCharacterMapper.mapper.toMovieCharacter(movieCharacterDAO.getById(connection, id).orElse(null));
        return Optional.of(movieCharacter);
    }

}
