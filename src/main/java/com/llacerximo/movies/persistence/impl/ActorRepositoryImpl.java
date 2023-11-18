package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.ResourceNotFoundException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.domain.repository.ActorRepository;
import com.llacerximo.movies.mapper.ActorMapper;
import com.llacerximo.movies.persistence.DAO.ActorDAO;
import com.llacerximo.movies.persistence.model.ActorEntity;
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
public class ActorRepositoryImpl implements ActorRepository {

    @Autowired
    ActorDAO actorDAO;

//    @Override
//    public List<Actor> getAll() {
//        try (Connection connection = DBUtil.open(true)){
//            List<ActorEntity>
//        }
//            DBUtil.close(connection);
//            return actors;
//        } catch (DBConnectionException e) {
//            throw e;
//        } catch (SQLException e) {
//            throw new SQLStatmentException("SQL: " + SQL);
//        }
//    }

    @Override
    public List<Actor> getAllPaginated(Integer page, Integer pageSize) {
        try (Connection connection = DBUtil.open(true)){
            List<ActorEntity> actorEntities = actorDAO.getAllPaginated(connection, page, pageSize);
            return actorEntities.stream()
                    .map(actorEntity -> ActorMapper.mapper.toActor(actorEntity))
                    .toList();
        } catch (DBConnectionException e) {
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Actor> getById(Integer id) {
        try (Connection connection = DBUtil.open(true)){
            Optional<ActorEntity> actorEntity = actorDAO.findById(connection, id);
            if (actorEntity.isEmpty())
                return Optional.empty();
            return Optional.of(ActorMapper.mapper.toActor(actorEntity.get()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getTotalRecords() {
        try(Connection connection = DBUtil.open(true)){
            return actorDAO.getTotalRecords(connection);
        } catch (SQLException e){
            throw new RuntimeException("Error en el conteo " + e.getMessage());
        }
    }

    public Integer insert(Actor actor) {
        Connection connection = DBUtil.open(true);
        Integer id = actorDAO.insert(connection, ActorMapper.mapper.toActorEntity(actor));
        DBUtil.close(connection);
        return id;
    }

    @Override
    public void update(Actor actor) {
        Connection connection = DBUtil.open(true);
        actorDAO.update(connection, ActorMapper.mapper.toActorEntity(actor));
        DBUtil.close(connection);
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DBUtil.open(true);
        actorDAO.delete(connection, id);
        DBUtil.close(connection);
    }

    @Override
    public Optional<Actor> getByCharacterId(Integer characterId) {
        Connection connection = DBUtil.open(true);
        Actor actor = ActorMapper.mapper.toActor(actorDAO.getByCharacterId(connection, characterId).orElse(null));
        return Optional.of(actor);
    }

//    @Override
//    public List<Actor> getByMovieId(Integer movieId) {
//        Connection connection = DBUtil.open(true);
//        List<ActorEntity> actorEntities = actorDAO.findByMovieId(connection, movieId);
//        return actorEntities.stream()
//                .map(actorEntity -> ActorMapper.mapper.toActor(actorEntity))
//                .toList();
//    }
}
