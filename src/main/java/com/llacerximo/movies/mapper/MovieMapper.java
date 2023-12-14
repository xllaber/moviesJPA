package com.llacerximo.movies.mapper;

import com.llacerximo.movies.controller.model.movie.MovieCreateWeb;
import com.llacerximo.movies.controller.model.movie.MovieDetailWeb;
import com.llacerximo.movies.controller.model.movie.MovieListWeb;
import com.llacerximo.movies.controller.model.movie.MovieUpdateWeb;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.persistence.model.MovieEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

    MovieListWeb toMovieListWeb(Movie movie);
    @Mapping(target = "characters", expression = "java(MovieCharacterMapper.mapper.toMovieCharacterListWebList(movie.getCharacters()))")
    MovieDetailWeb toMovieDetailWeb(Movie movie);

    @Mapping(target = "id", expression = "java(resultSet.getInt(\"id\"))")
    @Mapping(target = "title", expression = "java(resultSet.getString(\"title\"))")
    @Mapping(target = "year", expression = "java(resultSet.getInt(\"year\"))")
    @Mapping(target = "runtime", expression = "java(resultSet.getInt(\"runtime\"))")
    MovieEntity toMovieEntity(ResultSet resultSet) throws SQLException;
    MovieEntity toMovieEntity(Movie movie);

    @Mapping(target = "director", ignore = true)
    @Mapping(target = "characters", ignore = true)
    @Named("toMovie")
    Movie toMovie(MovieEntity movieEntity);

    @Mapping(target = "director", expression = "java(DirectorMapper.mapper.toDirector(movieEntity.getDirectorEntity()))")
    @Mapping(target = "characters", expression = "java(MovieCharacterMapper.mapper.toMovieCharacters(movieEntity.getMovieCharacterEntities()))")
    @Named("toMovieWithDirectorAndCharacterMovies")
    Movie toMovieWithDirectorAndCharacterMovies(MovieEntity movieEntity);

    @Mapping(target = "director", ignore = true)
    @Mapping(target = "characterMovies", ignore = true)
    @IterableMapping(qualifiedByName = "toMovie")
    @Named("toMovieList")
    List<Movie> toMovieList(List<MovieEntity> movieEntities);

    Movie toMovie(MovieDetailWeb movieDetailWeb);
    @Mapping(target ="director", ignore = true)
    @Mapping(target ="characters", ignore = true)
    Movie toMovie(MovieCreateWeb movieCreateWeb);
    Movie toMovie(MovieUpdateWeb movieUpdateWeb);
}
