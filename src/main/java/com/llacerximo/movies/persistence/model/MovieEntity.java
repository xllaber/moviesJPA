package com.llacerximo.movies.persistence.model;

import com.llacerximo.movies.persistence.DAO.DirectorDAO;
import com.llacerximo.movies.persistence.DAO.MovieCharacterDAO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private Integer year;
    private Integer runtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private DirectorEntity directorEntity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "movie_id")
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
