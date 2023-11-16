package com.llacerximo.movies.persistence.model;

import com.llacerximo.movies.persistence.DAO.ActorDAO;
import lombok.Data;

import java.sql.Connection;

@Data
public class MovieCharacterEntity {

    private Integer id;
    private String character;
    private ActorEntity actorEntity;

    public MovieCharacterEntity(Integer id, String character) {
        this.id = id;
        this.character = character;
    }

    public ActorEntity getActorEntity(Connection connection, ActorDAO actorDAO) {
        if (this.actorEntity == null)
            this.actorEntity = actorDAO.getByCharacterId(connection, this.id).orElse(null);
        return this.actorEntity;
    }

}
