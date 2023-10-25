package com.llacerximo.movies.mapper;

import com.llacerximo.movies.controller.model.director.DirectorCreateWeb;
import com.llacerximo.movies.controller.model.director.DirectorDetailWeb;
import com.llacerximo.movies.controller.model.director.DirectorListWeb;
import com.llacerximo.movies.domain.entity.Director;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    DirectorMapper mapper = Mappers.getMapper(DirectorMapper.class);
    DirectorDetailWeb toDirectorDetailWeb(Director director);
    DirectorListWeb toDirectorListWeb(Director director);
    Director toDirector(DirectorCreateWeb director);

}
