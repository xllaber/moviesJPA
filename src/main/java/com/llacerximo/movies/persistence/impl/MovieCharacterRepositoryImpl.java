package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.MovieCharacter;
import com.llacerximo.movies.domain.repository.MovieCharacterRepository;
import com.llacerximo.movies.mapper.MovieCharacterMapper;
import com.llacerximo.movies.persistence.DAO.MovieCharacterDAO;
import com.llacerximo.movies.persistence.model.MovieCharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class MovieCharacterRepositoryImpl implements MovieCharacterRepository {

    @Autowired
    MovieCharacterDAO movieCharacterDAO;

    @Override
    public Optional<MovieCharacter> getById(Integer id) {
        Connection connection = DBUtil.open(true);
        Optional<MovieCharacterEntity> movieCharacterEntity = movieCharacterDAO.getById(connection, id);
        if (movieCharacterEntity.isEmpty())
            return Optional.empty();

        return Optional.ofNullable(MovieCharacterMapper.mapper.toMovieCharacter(movieCharacterEntity.get()));
    }

    @Override
    public Optional<MovieCharacter> getByMovieId(Integer id) {
        Connection connection = DBUtil.open(true);
        return Optional.ofNullable(MovieCharacterMapper.mapper.toMovieCharacter(movieCharacterDAO.getByMovieId(connection, id).get()));
    }
}
