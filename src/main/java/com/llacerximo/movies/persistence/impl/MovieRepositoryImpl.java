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

//    @Override
//    public List<Movie> getAll() {
//        final String SQL = "SELECT * FROM movies";
//        List<Movie> movies = new ArrayList<>();
//        try (Connection connection = DBUtil.open()){
//            ResultSet resultSet = DBUtil.select(connection, SQL, null);
//            while (resultSet.next()) {
//                movies.add(
//                        new Movie(
//                                resultSet.getInt("id"),
//                                resultSet.getString("title"),
//                                resultSet.getInt("year"),
//                                resultSet.getInt("runtime")
//                        )
//                );
//            }
//            DBUtil.close(connection);
//            return movies;
//        } catch (DBConnectionException e) {
//            throw e;
//        } catch (SQLException e) {
//            throw new SQLStatmentException("SQL: " + SQL);
//        }
//    }

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
    public Integer getTotalRecords() {
        try(Connection connection = DBUtil.open(true)) {
            return movieDAO.getTotalRecords(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
