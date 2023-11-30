package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.domain.repository.DirectorRepository;
import com.llacerximo.movies.mapper.DirectorMapper;
import com.llacerximo.movies.persistence.DAO.DirectorDAO;
import com.llacerximo.movies.persistence.model.DirectorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepositoryImpl implements DirectorRepository {

    @Autowired
    DirectorDAO directorDAO;

//    @Override
//    public List<Director> getAll() {
//        final String SQL = "SELECT * FROM directors";
//        List<Director> directors = new ArrayList<>();
//        try (Connection connection = DBUtil.open()){
//            ResultSet resultSet = DBUtil.select(connection, SQL, null);
//            while (resultSet.next()) {
//                directors.add(
//                        new Director(
//                                resultSet.getString("name"),
//                                resultSet.getInt("birthYear"),
//                                resultSet.getInt("deathYear"),
//                                resultSet.getInt("id")
//                        )
//                );
//            }
//            DBUtil.close(connection);
//            return directors;
//        } catch (DBConnectionException e) {
//            throw e;
//        } catch (SQLException e) {
//            throw new SQLStatmentException("SQL: " + SQL);
//        }
//    }

    @Override
    public List<Director> getAllPaginated(Integer page, Integer pageSize) {
        try (Connection connection = DBUtil.open(true)){
            List<DirectorEntity> directorEntities = directorDAO.getAllPaginated(connection, page, pageSize);
            return directorEntities.stream()
                    .map(directorEntity -> DirectorMapper.mapper.toDirector(directorEntity))
                    .toList();
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Director> getById(Integer id) {
        try (Connection connection = DBUtil.open(true)){
            Optional<DirectorEntity> directorEntity = directorDAO.findById(connection, id);
            if (directorEntity.isEmpty())
                return Optional.empty();
            return Optional.ofNullable(DirectorMapper.mapper.toDirector(directorEntity.get()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getTotalRecords() {
        try(Connection connection = DBUtil.open(true)){
            return directorDAO.getTotalRecords(connection);
        } catch (SQLException e){
            throw new RuntimeException("Error en el conteo" + e.getMessage());
        }
    }

    @Override
    public Integer insert(Director director) {
        Connection connection = DBUtil.open(true);
        Integer id = directorDAO.insert(connection, DirectorMapper.mapper.toDirectorEntity(director));
        DBUtil.close(connection);
        return id;
    }

    @Override
    public void update(Director director) {
        Connection connection = DBUtil.open(true);
        directorDAO.update(connection, DirectorMapper.mapper.toDirectorEntity(director));
        DBUtil.close(connection);
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DBUtil.open(true);
        directorDAO.delete(connection, id);
        DBUtil.close(connection);
    }

    @Override
    public Optional<Director> getByMovieId(Integer movieId) {
        Connection connection = DBUtil.open(true);
        return Optional.ofNullable(DirectorMapper.mapper.toDirector(directorDAO.findByMovieId(connection, movieId).get()));
    }
}
