package com.llacerximo.movies.mapper;

import com.llacerximo.movies.controller.model.director.DirectorCreateWeb;
import com.llacerximo.movies.controller.model.director.DirectorDetailWeb;
import com.llacerximo.movies.controller.model.director.DirectorListWeb;
import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.persistence.model.DirectorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    DirectorMapper mapper = Mappers.getMapper(DirectorMapper.class);
    DirectorDetailWeb toDirectorDetailWeb(Director director);
    DirectorListWeb toDirectorListWeb(Director director);
    Director toDirector(DirectorCreateWeb director);
    Director toDirector(DirectorEntity director);
    DirectorEntity toDirectorEntity(Director director);

    @Mapping(target = "id", expression = "java(resultSet.getInt(\"id\"))")
    @Mapping(target = "name", expression = "java(resultSet.getString(\"name\"))")
    @Mapping(target = "birthYear", expression = "java(resultSet.getInt(\"birthYear\"))")
    @Mapping(target = "deathYear", expression = "java(resultSet.getInt(\"deathYear\"))")
    DirectorEntity toDirectorEntity(ResultSet resultSet) throws SQLException;

}
