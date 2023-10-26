package com.llacerximo.movies.mapper;

import com.llacerximo.movies.controller.model.movie.MovieDetailWeb;
import com.llacerximo.movies.controller.model.movie.MovieListWeb;
import com.llacerximo.movies.domain.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper mapper = Mappers.getMapper(MovieMapper.class);
    MovieListWeb toMovieListWeb(Movie movie);
    MovieDetailWeb toMovieDetailWeb(Movie movie);

}
