package com.llacerximo.movies.persistence.DAO;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.mapper.ActorMapper;
import com.llacerximo.movies.persistence.model.ActorEntity;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ActorDAO {

    public List<ActorEntity> getAllPaginated(Connection connection, Integer page, Integer pageSize) {
        String sql = "SELECT * FROM directors";
        int offset = (page - 1) * pageSize;
        sql += String.format(" LIMIT %d, %d", offset, pageSize);
        List<ActorEntity> actorEntities = new ArrayList<>();
        try {
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                actorEntities.add(ActorMapper.mapper.toActorEntity(resultSet));
            }
            DBUtil.close(connection);
            return actorEntities;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + sql);
        }
    }

    public Optional<ActorEntity> findById(Connection connection, Integer id){
        final String SQL = "SELECT * FROM ACTORS WHERE id = ? LIMIT 1";
        try {
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(id));
            return Optional.ofNullable(resultSet.next() ? ActorMapper.mapper.toActorEntity(resultSet) : null);
        } catch (SQLException e){
            throw new SQLStatmentException("SQL: " + SQL);
        }
    }

    public Integer insert(Connection connection, ActorEntity actorEntity) {
        final String SQL = "INSERT INTO directors (name, birthYear, deathYear) VALUES (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(actorEntity.getName());
        params.add(actorEntity.getBirthYear());
        params.add(actorEntity.getDeathYear());
        int id = DBUtil.insert(connection, SQL, params);
        return id;
    }

    public void update(Connection connection, ActorEntity actorEntity) {
        final String SQL = "UPDATE actors SET name = ?, birthYear = ?, deathYear = ? WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(actorEntity.getName());
        params.add(actorEntity.getBirthYear());
        params.add(actorEntity.getDeathYear());
        params.add(actorEntity.getId());
        DBUtil.update(connection, SQL, params);
        DBUtil.close(connection);
    }

    public void delete(Connection connection, Integer id) {
        final String SQL = "DELETE FROM actors WHERE id = ?";
        DBUtil.delete(connection, SQL, List.of(id));
        DBUtil.close(connection);
    }

    public List<ActorEntity> findByMovieId(Connection connection, int movieId) {
        List<ActorEntity> actorEntities = new ArrayList<>();
        final String SQL = """
            SELECT a.* FROM actors a 
            INNER JOIN  actors_movies ma ON ma.actor_id = a.id
            INNER JOIN movies m ON ma.movie_id = m.id
            WHERE m.id = ?
        """;
        try{
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(movieId));
            while(resultSet.next()){
                actorEntities.add(ActorMapper.mapper.toActorEntity(resultSet));
            }
            DBUtil.close(connection);
            return actorEntities;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Integer getTotalRecords(Connection connection) {
        final String SQL = "SELECT COUNT(*) FROM actors";
        try{
            ResultSet resultSet = DBUtil.select(connection, SQL, null);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e){
            throw new RuntimeException("SQL: " + SQL);
        }
    }

}
