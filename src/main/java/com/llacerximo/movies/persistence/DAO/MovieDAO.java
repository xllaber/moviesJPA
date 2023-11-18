package com.llacerximo.movies.persistence.DAO;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.mapper.MovieMapper;
import com.llacerximo.movies.persistence.model.ActorEntity;
import com.llacerximo.movies.persistence.model.MovieCharacterEntity;
import com.llacerximo.movies.persistence.model.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MovieDAO {

    @Autowired
    ActorDAO actorDAO;

    public List<MovieEntity> getAllPaginated(Connection connection, Integer page, Integer pageSize) {
        String sql = "SELECT * FROM movies";
        int offset = (page - 1) * pageSize;
        sql += String.format(" LIMIT %d, %d", offset, pageSize);
        List<MovieEntity> movieEntities = new ArrayList<>();
        try {
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                movieEntities.add(MovieMapper.mapper.toMovieEntity(resultSet));
            }
            DBUtil.close(connection);
            return movieEntities;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + sql + e.getMessage());
        }
    }

    public Optional<MovieEntity> findById(Connection connection, Integer id) {
        final String SQL = "SELECT * FROM movies WHERE id = ? LIMIT 1";
        try{
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(id));
            return Optional.ofNullable(resultSet.next() ? MovieMapper.mapper.toMovieEntity(resultSet) : null);
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + SQL + e.getMessage());
        }
    }

    public Integer getTotalRecords(Connection connection) {
        final String SQL = "SELECT COUNT(*) FROM movies";
        try {
            ResultSet resultSet = DBUtil.select(connection, SQL, null);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("SQL: " + SQL + e.getMessage());
        }
    }

    public Integer insert(Connection connection, MovieEntity movieEntity) throws SQLException {
        try {
            final String SQL = "INSERT INTO movies (title, year, runtime, director_id) VALUES (?, ?, ?, ?)";
            List<Object> params = new ArrayList<>();
            params.add(movieEntity.getTitle());
            params.add(movieEntity.getYear());
            params.add(movieEntity.getRuntime());
            params.add(movieEntity.getDirectorEntity().getId());
            Integer id =  DBUtil.insert(connection, SQL, params);
            movieEntity.getMovieCharacterEntities().forEach(movieCharacterEntity -> addCharacter(connection, id, movieCharacterEntity));
            connection.commit();
            return id;
        } catch (Exception e){
            connection.rollback();
            throw new RuntimeException(e.getCause() + e.getMessage());
        }
    }

    public void addCharacter(Connection connection, Integer movieId, MovieCharacterEntity movieCharacterEntity) {
        final String SQL = "insert into actors_movies (actor_id, movie_id, characters) values (?, ?, ?)";
        ActorEntity actorEntity = movieCharacterEntity.getActorEntity(connection, actorDAO);
        List<Object> params = new ArrayList<>();
        params.add(actorEntity.getId());
        params.add(movieId);
        params.add(movieCharacterEntity.getCharacter());
        DBUtil.insert(connection, SQL, params);
    }

    public void update(Connection connection, MovieEntity movieEntity) {
        try {
            String sql = "update movies set title = ?, year = ?, runtime = ?, director_id = ? where id = ?";
            List<Object> params = new ArrayList<>();
            params.add(movieEntity.getTitle());
            params.add(movieEntity.getYear());
            params.add(movieEntity.getRuntime());
            params.add(movieEntity.getDirectorEntity().getId());
            params.add(movieEntity.getId());
            movieEntity.getMovieCharacterEntities().forEach(movieCharacterEntity -> updateCharacter(connection, movieEntity.getId(), movieCharacterEntity));
            DBUtil.update(connection, sql, params);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtil.close(connection);
    }

    public void updateCharacter(Connection connection, Integer movieId, MovieCharacterEntity movieCharacterEntity) {
        try {
            String sql = "update actors_movies set actor_id = ?, characters = ? where movie_id = ? and id = ?";
            List<Object> params = new ArrayList<>();
            params.add(movieCharacterEntity.getActorEntity().getId());
            params.add(movieCharacterEntity.getCharacter());
            params.add(movieId);
            params.add(movieCharacterEntity.getId());
            DBUtil.update(connection, sql, params);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Connection connection, Integer id) {
        try {
            String sql = "delete from movies where id = ?";
            List<Object> params = List.of(id);
            DBUtil.delete(connection, sql, params);
            deleteCharacters(connection, id);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtil.close(connection);
    }

    public void deleteCharacters(Connection connection, Integer id) {
        String sql = "delete from actors_movies where movie_id = ?";
        List<Object> params = List.of(id);
        DBUtil.delete(connection, sql, params);
    }

}
