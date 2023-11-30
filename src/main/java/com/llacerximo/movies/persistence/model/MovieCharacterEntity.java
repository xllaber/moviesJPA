package com.llacerximo.movies.persistence.model;

import com.llacerximo.movies.persistence.DAO.ActorDAO;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Connection;

@Data
@Entity
@Table(name = "actors_movies")
public class MovieCharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
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
