package com.llacerximo.movies.persistence.DAO;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.mapper.MovieCharacterMapper;
import com.llacerximo.movies.persistence.model.MovieCharacterEntity;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class MovieCharacterDAO {

    public Optional<MovieCharacterEntity> getById(Connection connection, Integer id) {
        final String SQL = "Select * from actors_movies where id = ?";
        try {
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(id));
            return Optional.ofNullable(resultSet.next() ? MovieCharacterMapper.mapper.toMovieCharacterEntity(resultSet) : null);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<MovieCharacterEntity> getByMovieId(Connection connection, Integer movieId) {
        final String SQL = "select a.* from actors a inner join actors_movies am on a.id = am.actor_id where movie_id = ?";
        try {
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(movieId));
            return Optional.ofNullable(resultSet.next() ? MovieCharacterMapper.mapper.toMovieCharacterEntity(resultSet) : null);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
