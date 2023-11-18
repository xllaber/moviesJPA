package com.llacerximo.movies.mapper;

import com.llacerximo.movies.controller.model.MovieCharacter.MovieCharacterListWeb;
import com.llacerximo.movies.controller.model.MovieCharacter.MovieCharacterUpdateWeb;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.MovieCharacter;
import com.llacerximo.movies.persistence.model.MovieCharacterEntity;
import com.llacerximo.movies.persistence.model.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieCharacterMapper {

    MovieCharacterMapper mapper = Mappers.getMapper(MovieCharacterMapper.class);

    @Mapping(target = "actorId", expression = "java(movieCharacter.getActor().getId())")
    @Mapping(target = "actorName", expression = "java(movieCharacter.getActor().getName())")
    MovieCharacterListWeb toMovieCharacterListWeb(MovieCharacter movieCharacter);
    @Mapping(target = "actor", expression = "java(ActorMapper.mapper.toActor(movieCharacterEntity.getActorEntity()))")
    MovieCharacter toMovieCharacter(MovieCharacterEntity movieCharacterEntity);
    @Mapping(target = "actorEntity", expression = "java(ActorMapper.mapper.toActorEntity(movieCharacter.getActor()))")
    MovieCharacterEntity toMovieCharacterEntity(MovieCharacter movieCharacter);

    @Mapping(target = "id", expression = "java(resultSet.getInt(\"id\"))")
    @Mapping(target = "character", expression = "java(resultSet.getString(\"characters\"))")
//    @Mapping(target = "movieId", expression = "java(resultSet.getInt(\"movie_id\"))")
//    @Mapping(target = "actorId", expression = "java(resultSet.getInt(\"actor_id\"))")
    MovieCharacterEntity toMovieCharacterEntity(ResultSet resultSet) throws SQLException;

    default List<MovieCharacterListWeb> toMovieCharacterListWebList(List<MovieCharacter> movieCharacters){
        if (movieCharacters == null) return null;
        List<MovieCharacterListWeb> movieCharacterListWebs = movieCharacters.stream()
                .map(MovieCharacterMapper.mapper::toMovieCharacterListWeb)
                .toList();
        return movieCharacterListWebs;
    }

    default List<MovieCharacter> toMovieCharacterList(List<MovieCharacterEntity> movieCharacterEntities){
        if (movieCharacterEntities == null) return null;
        List<MovieCharacter> movieCharacters = movieCharacterEntities.stream()
                .map(MovieCharacterMapper.mapper::toMovieCharacter)
                .toList();
        return movieCharacters;
    }

    default List<MovieCharacterEntity> toMovieCharacterEntityList(List<MovieCharacter> movieCharacters){
        if (movieCharacters == null) return null;
        List<MovieCharacterEntity> movieCharacterEntities = movieCharacters.stream()
                .map(MovieCharacterMapper.mapper::toMovieCharacterEntity)
                .toList();
        return movieCharacterEntities;
    }

//    @Named("actorIdToActor")
//    default Actor mapActorIdToActor(Integer actorId) {
//
//    }
}
