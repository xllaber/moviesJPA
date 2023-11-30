package com.llacerximo.movies.persistence.impl;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.entity.MovieCharacter;
import com.llacerximo.movies.domain.repository.MovieRepository;
import com.llacerximo.movies.mapper.MovieMapper;
import com.llacerximo.movies.persistence.DAO.ActorDAO;
import com.llacerximo.movies.persistence.DAO.DirectorDAO;
import com.llacerximo.movies.persistence.DAO.MovieCharacterDAO;
import com.llacerximo.movies.persistence.DAO.MovieDAO;
import com.llacerximo.movies.persistence.model.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    MovieDAO movieDAO;
    @Autowired
    DirectorDAO directorDAO;
    @Autowired
    MovieCharacterDAO movieCharacterDAO;
    @Autowired
    ActorDAO actorDAO;

    @Override
    public List<Movie> getAllPaginated(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<MovieEntity> movieEntities = movieDAO.findAll(pageable).stream().toList();
       return MovieMapper.mapper.toMovieList(movieEntities);
    }

    @Override
    public Optional<Movie> findById(Integer id) {
        try (Connection connection = DBUtil.open(true)){
            MovieEntity movieEntity = movieDAO.findById(connection, id).get();
            if(movieEntity != null) {
                movieEntity.getDirectorEntity(connection, directorDAO);
                movieEntity.getMovieCharacterEntity(connection, movieCharacterDAO).forEach(
                        movieCharacterEntity -> movieCharacterEntity.getActorEntity(connection, actorDAO)
                );
            }
            Movie movie = MovieMapper.mapper.toMovie(movieEntity);
            return Optional.of(movie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer insert(Movie movie) {
        try(Connection connection = DBUtil.open(false)) {
            MovieEntity movieEntity =  MovieMapper.mapper.toMovieEntity(movie);
            movieEntity.setDirectorEntity(DirectorMapper.mapper.toDirectorEntity(movie.getDirector()));
            movieEntity.setMovieCharacterEntities(MovieCharacterMapper.mapper.toMovieCharacterEntityList(movie.getCharacters()));
            Integer id = movieDAO.insert(connection, movieEntity);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(Movie movie) {
        Connection connection = DBUtil.open(false);
        MovieEntity movieEntity = MovieMapper.mapper.toMovieEntity(movie);
        movieEntity.setDirectorEntity(DirectorMapper.mapper.toDirectorEntity(movie.getDirector()));
        movieEntity.setMovieCharacterEntities(MovieCharacterMapper.mapper.toMovieCharacterEntityList(movie.getCharacters()));
        movieDAO.update(connection, movieEntity);
        DBUtil.close(connection);
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DBUtil.open(false);
        movieDAO.delete(connection, id);
        DBUtil.close(connection);
    }

    @Override
    public void addCharacterToMovie(Integer movieId, MovieCharacter movieCharacter) {
        Connection connection = DBUtil.open(true);
        MovieCharacterEntity movieCharacterEntity = MovieCharacterMapper.mapper.toMovieCharacterEntity(movieCharacter);
        movieDAO.addCharacterToMovie(connection, movieId, movieCharacterEntity);
    }

    @Override
    public void updateCharacterOfMovie(MovieCharacter movieCharacter, Integer movieId) {
        Connection connection = DBUtil.open(true);
        MovieCharacterEntity movieCharacterEntity = MovieCharacterMapper.mapper.toMovieCharacterEntity(movieCharacter);
        movieDAO.updateCharacterOfMovie(connection, movieCharacterEntity, movieId);
    }

    @Override
    public void deleteCharacterOfMovie(Integer characterId, Integer movieId) {
        Connection connection = DBUtil.open(true);
        movieDAO.deleteCharacterOfMovie(connection, characterId, movieId);
    }

    @Override
    public Integer getTotalRecords() {
            return (Integer) (int) movieDAO.count();
    }

}
