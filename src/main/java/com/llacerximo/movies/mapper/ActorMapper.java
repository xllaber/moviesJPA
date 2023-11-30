package com.llacerximo.movies.mapper;

import com.llacerximo.movies.controller.model.actor.ActorCreateWeb;
import com.llacerximo.movies.controller.model.actor.ActorDetailWeb;
import com.llacerximo.movies.controller.model.actor.ActorListWeb;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.persistence.model.ActorEntity;
import com.llacerximo.movies.persistence.model.DirectorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorMapper mapper = Mappers.getMapper(ActorMapper.class);
    ActorDetailWeb toActorDetailWeb(Actor actor);
    ActorListWeb toActorListWeb(Actor actor);
    Actor toActor(ActorCreateWeb actor);
    Actor toActor(ActorEntity actorEntity);
    ActorEntity toActorEntity(Actor actor);

    @Mapping(target = "id", expression = "java(resultSet.getInt(\"id\"))")
    @Mapping(target = "name", expression = "java(resultSet.getString(\"name\"))")
    @Mapping(target = "birthYear", expression = "java(resultSet.getInt(\"birthYear\"))")
    @Mapping(target = "deathYear", expression = "java(resultSet.getInt(\"deathYear\"))")
    ActorEntity toActorEntity(ResultSet resultSet) throws SQLException;
}
