package com.llacerximo.movies.persistence.DAO;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.mapper.DirectorMapper;
import com.llacerximo.movies.persistence.model.DirectorEntity;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DirectorDAO {

    public List<DirectorEntity> getAllPaginated(Connection connection, Integer page, Integer pageSize) {
        String sql = "SELECT * FROM directors";
        int offset = (page - 1) * pageSize;
        sql += String.format(" LIMIT %d, %d", offset, pageSize);
        List<DirectorEntity> directorEntites = new ArrayList<>();
        try {
            ResultSet resultSet = DBUtil.select(connection, sql, null);
            while (resultSet.next()) {
                directorEntites.add(DirectorMapper.mapper.toDirectorEntity(resultSet));
            }
            DBUtil.close(connection);
            return directorEntites;
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new SQLStatmentException("SQL: " + sql);
        }
    }

    public Optional<DirectorEntity> findById(Connection connection, Integer id){
        final String SQL = "SELECT * FROM DIRECTORS WHERE id = ? LIMIT 1";
        try {
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(id));
            return Optional.ofNullable(resultSet.next() ? DirectorMapper.mapper.toDirectorEntity(resultSet) : null);
        } catch (SQLException e){
            throw new SQLStatmentException("SQL: " + SQL);
        }
    }

    public Integer insert(Connection connection, DirectorEntity directorEntity) {
        final String SQL = "INSERT INTO directors (name, birthYear, deathYear) VALUES (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(directorEntity.getName());
        params.add(directorEntity.getBirthYear());
        params.add(directorEntity.getDeathYear());
        int id = DBUtil.insert(connection, SQL, params);
        return id;
    }

    public void update(Connection connection, DirectorEntity directorEntity) {
        final String SQL = "UPDATE directors SET name = ?, birthYear = ?, deathYear = ? WHERE id = ?";
        List<Object> params = new ArrayList<>();
        params.add(directorEntity.getName());
        params.add(directorEntity.getBirthYear());
        params.add(directorEntity.getDeathYear());
        params.add(directorEntity.getId());
        DBUtil.update(connection, SQL, params);
        DBUtil.close(connection);
    }

    public void delete(Connection connection, Integer id) {
        final String SQL = "DELETE FROM directors WHERE id = ?";
        DBUtil.delete(connection, SQL, List.of(id));
        DBUtil.close(connection);
    }

    public Optional<DirectorEntity> findByMovieId(Connection connection, Integer movieId) {
        final String SQL = """
            SELECT d.* FROM directors d 
            INNER JOIN  movies m ON m.director_id = d.id
            WHERE m.id = ?
            LIMIT 1
        """;
        try{
            ResultSet resultSet = DBUtil.select(connection, SQL, List.of(movieId));
            return Optional.ofNullable(resultSet.next()? DirectorMapper.mapper.toDirectorEntity(resultSet):null);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Integer getTotalRecords(Connection connection) {
        final String SQL = "SELECT COUNT(*) FROM directors";
        try {
            ResultSet resultSet = DBUtil.select(connection, SQL, null);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("SQL: " + SQL);
        }
    }

}
