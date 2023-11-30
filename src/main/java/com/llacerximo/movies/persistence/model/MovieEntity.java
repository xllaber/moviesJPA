package com.llacerximo.movies.persistence.model;

import com.llacerximo.movies.persistence.DAO.DirectorDAO;
import com.llacerximo.movies.persistence.DAO.MovieCharacterDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    private Integer id;
    private String title;
    private Integer year;
    private Integer runtime;
    private DirectorEntity directorEntity;
    private List<MovieCharacterEntity> movieCharacterEntities;

    public MovieEntity(int id, String title, int year, int runtime) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runtime = runtime;
    }

    public DirectorEntity getDirectorEntity(Connection connection, DirectorDAO directorDAO) {
        if (this.directorEntity != null) return this.directorEntity;
        this.directorEntity = directorDAO.findByMovieId(connection, this.id).orElse(null);
        return this.directorEntity;
    }

    public List<MovieCharacterEntity> getMovieCharacterEntity(Connection connection, MovieCharacterDAO movieCharacterDAO) {
        if (this.movieCharacterEntities != null) return this.movieCharacterEntities;
        this.movieCharacterEntities = movieCharacterDAO.getByMovieId(connection, this.id);
        return this.movieCharacterEntities;
    }

}
