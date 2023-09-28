package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Value("${default.page.size}")
    private Integer LIMIT;

    @Override
    public List<Movie> getAll() {
        final String SQL = "SELECT * FROM movies";
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, SQL, null);
            while (resultSet.next()) {
                movies.add(
                        new Movie(
                                resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getInt("year"),
                                resultSet.getInt("runtime")
                        )
                );
            }
            DBUtil.close(connection);
            return movies;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + SQL);
        }
    }

    @Override
    public List<Movie> getAllPaginated(Optional<Integer> page) {
        String sql = "SELECT * FROM movies";
        if (page.isPresent()){
            int offset = (page.get() - 1) * LIMIT;
            sql += String.format(" LIMIT %d, %d", offset, LIMIT);
        }
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                movies.add(
                        new Movie(
                                resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getInt("year"),
                                resultSet.getInt("runtime")
                        )
                );
            }
            DBUtil.close(connection);
            return movies;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + sql);
        }
    }

    @Override
    public Integer getTotalRecords() {
        final String SQL = "SELECT COUNT(*) FROM movies";
        try(Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, SQL, null);
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
            else {
                throw new ResourceNotFoundException("No hay registros en la BD");
            }
        } catch (SQLException e){
            throw new RuntimeException("Error en el conteo: " + SQL + " " + e.getMessage());
        }
    }

    @Override
    public Movie findById(int id) {
        final String SQL = "SELECT * FROM movies WHERE id = ? LIMIT 1";
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(id));
            if(resultSet.next()) {
                return new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("year"),
                        resultSet.getInt("runtime")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + SQL);
        }
    }
}
