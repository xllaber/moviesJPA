package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.domain.repository.MovieRepository;
import com.llacerximo.movies.mapper.MovieMapper;
import com.llacerximo.movies.persistence.DAO.MovieDAO;
import com.llacerximo.movies.persistence.model.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    MovieDAO movieDAO;

    @Override
    public List<Movie> getAllPaginated(Integer page, Integer pageSize) {
        try(Connection connection = DBUtil.open(true)) {
            List<MovieEntity> movieEntities = movieDAO.getAllPaginated(connection, page, pageSize);
            return movieEntities.stream()
                    .map(movieEntity -> MovieMapper.mapper.toMovie(movieEntity))
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Movie> findById(Integer id) {
        try (Connection connection = DBUtil.open(true)){
            Optional<MovieEntity> movieEntity = movieDAO.findById(connection, id);
            if(movieEntity.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(MovieMapper.mapper.toMovie(movieEntity.get()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer insert(Movie movie) {
        try(Connection connection = DBUtil.open(false)) {
            MovieEntity movieEntity =  MovieMapper.mapper.toMovieEntity(movie);
            Integer id = movieDAO.insert(connection, movieEntity);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Movie movie) {

    }

    @Override
    public Integer getTotalRecords() {
        try(Connection connection = DBUtil.open(true)) {
            return movieDAO.getTotalRecords(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
