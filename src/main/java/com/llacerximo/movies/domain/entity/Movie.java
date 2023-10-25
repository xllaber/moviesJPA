package com.llacerximo.movies.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Movie {

    private Integer id;
    private String title;
    private Integer year;
    private Integer runtime;
    private Director director;
    private List<Actor> actors;

    public Movie(Integer id, String title, Integer year, Integer runtime) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runtime = runtime;
    }

    public Movie(String title, Integer year, Integer runtime) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
    }

}
