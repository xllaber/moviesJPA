package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.persistence.DirectorRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepositoryImpl implements DirectorRepository {

    @Override
    public List<Director> getAll() {
        final String SQL = "SELECT * FROM directors";
        List<Director> directors = new ArrayList<>();
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, SQL, null);
            while (resultSet.next()) {
                directors.add(
                        new Director(
                                resultSet.getString("name"),
                                resultSet.getInt("birthYear"),
                                resultSet.getInt("deathYear"),
                                resultSet.getInt("id")
                        )
                );
            }
            DBUtil.close(connection);
            return directors;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + SQL);
        }
    }

    @Override
    public List<Director> getAllPaginated(Optional<Integer> page, Integer pageSize) {
        String sql = "SELECT * FROM directors";
        if (page.isPresent()){
            int offset = (page.get() - 1) * pageSize;
            sql += String.format(" LIMIT %d, %d", offset, pageSize);
        }
        List<Director> directors = new ArrayList<>();
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                directors.add(
                        new Director(
                                resultSet.getString("name"),
                                resultSet.getInt("birthYear"),
                                resultSet.getInt("deathYear"),
                                resultSet.getInt("id")
                        )
                );
            }
            DBUtil.close(connection);
            return directors;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + sql);
        }
    }

    @Override
    public Director getById(Integer id) {
        final String SQL = "SELECT * FROM directors WHERE id = ? LIMIT 1";
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(id));
            if(resultSet.next()) {
                return new Director(
                        resultSet.getString("name"),
                        resultSet.getInt("birthYear"),
                        resultSet.getInt("deathYear"),
                        resultSet.getInt("id")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + SQL);
        }
    }

    @Override
    public Integer getTotalRecords() {
        final String SQL = "SELECT COUNT(*) FROM directors";
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
    public Integer insert(Director director) {
        final String SQL = "INSERT INTO directors (name, birthYear, deathYear) VALUES (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(director.getName());
        params.add(director.getBirthYear());
        params.add(director.getDeathYear());
        Connection connection = DBUtil.open();
        Integer id = DBUtil.insert(connection, SQL, params);
        DBUtil.close(connection);
        return id;
    }

    @Override
    public void update(Director director) {
        final String SQL = "UPDATE directors SET name = ?, birthYear = ?, deathYear = ? WHERE id = ?";
        Connection connection = DBUtil.open();
        List<Object> params = new ArrayList<>();
        params.add(director.getName());
        params.add(director.getBirthYear());
        params.add(director.getDeathYear());
        params.add(director.getId());
        DBUtil.update(connection, SQL, params);
        DBUtil.close(connection);
    }

    @Override
    public void delete(Integer id) {
        final String SQL = "DELETE FROM directors WHERE id = ?";
        Connection connection = DBUtil.open();
        DBUtil.delete(connection, SQL, List.of(id));
        DBUtil.close(connection);
    }
}
