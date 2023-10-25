package com.llacerximo.movies.mapper;

import com.llacerximo.movies.controller.model.actor.ActorCreateWeb;
import com.llacerximo.movies.controller.model.actor.ActorDetailWeb;
import com.llacerximo.movies.controller.model.actor.ActorListWeb;
import com.llacerximo.movies.domain.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorMapper mapper = Mappers.getMapper(ActorMapper.class);
    ActorDetailWeb toActorDetailWeb(Actor actor);
    ActorListWeb toActorListWeb(Actor actor);
    Actor toActor(ActorCreateWeb actor);

}
