package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.persistence.ActorRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ActorRepositoryImpl implements ActorRepository {
    @Override
    public List<Actor> getAll() {
        final String SQL = "SELECT * FROM actors";
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, SQL, null);
            while (resultSet.next()) {
                actors.add(
                        new Actor(
                                resultSet.getString("name"),
                                resultSet.getInt("birthYear"),
                                resultSet.getInt("deathYear"),
                                resultSet.getInt("id")
                        )
                );
            }
            DBUtil.close(connection);
            return actors;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + SQL);
        }
    }

    @Override
    public List<Actor> getAllPaginated(Integer page, Integer pageSize) {
        String sql = "SELECT * FROM actors";
        int offset = (page - 1) * pageSize;
        sql += String.format(" LIMIT %d, %d", offset, pageSize);
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                actors.add(
                        new Actor(
                                resultSet.getString("name"),
                                resultSet.getInt("birthYear"),
                                resultSet.getInt("deathYear"),
                                resultSet.getInt("id")
                        )
                );
            }
            DBUtil.close(connection);
            return actors;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + sql);
        }
    }

    @Override
    public Actor getById(Integer id) {
        final String SQL = "SELECT * FROM actors WHERE id = ? LIMIT 1";
        try (Connection connection = DBUtil.open()){
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(id));
            if(resultSet.next()) {
                return new Actor(
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
        final String SQL = "SELECT COUNT(*) FROM actors";
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

    public Integer insert(Actor actor) {
        final String SQL = "INSERT INTO actors (name, birthYear, deathYear) VALUES (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(actor.getName());
        params.add(actor.getBirthYear());
        params.add(actor.getDeathYear());
        Connection connection = DBUtil.open();
        Integer id = DBUtil.insert(connection, SQL, params);
        DBUtil.close(connection);
        return id;
    }

    @Override
    public void update(Actor actor) {
        final String SQL = "UPDATE actors SET name = ?, birthYear = ?, deathYear = ? WHERE id = ?";
        Connection connection = DBUtil.open();
        List<Object> params = new ArrayList<>();
        params.add(actor.getName());
        params.add(actor.getBirthYear());
        params.add(actor.getDeathYear());
        params.add(actor.getId());
        DBUtil.update(connection, SQL, params);
        DBUtil.close(connection);
    }

    @Override
    public void delete(Integer id) {
        final String SQL = "DELETE FROM actors WHERE id = ?";
        Connection connection = DBUtil.open();
        DBUtil.delete(connection, SQL, List.of(id));
        DBUtil.close(connection);
    }
}
