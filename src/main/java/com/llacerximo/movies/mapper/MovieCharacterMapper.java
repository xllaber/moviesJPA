package com.llacerximo.movies.mapper;

import com.llacerximo.movies.domain.entity.MovieCharacter;
import com.llacerximo.movies.persistence.model.MovieCharacterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;

@Mapper(componentModel = "spring")
public interface MovieCharacterMapper {

    MovieCharacterMapper mapper = Mappers.getMapper(MovieCharacterMapper.class);

    MovieCharacter toMovieCharacter(MovieCharacterEntity movieCharacterEntity);
    MovieCharacterEntity toMovieCharacterEntity(MovieCharacter movieCharacter);

    @Mapping(target = "id", expression = "java(resultSet.getInt(\"id\"))")
    @Mapping(target = "name", expression = "java(resultSet.getString(\"name\"))")
//    @Mapping(target = "movieId", expression = "java(resultSet.getInt(\"movie_id\"))")
    @Mapping(target = "actorId", expression = "java(resultSet.getInt(\"actor_id\"))")
    MovieCharacterEntity toMovieCharacterEntity(ResultSet resultSet);
}
