package com.llacerximo.movies.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Movie {

    private int id;
    private String title;
    private int year;
    private int runTime;
    private Director director;
    private List<Actor> actors;

    public Movie(int id, String title, int year, int runTime) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runTime = runTime;
    }

    public Movie(String title, int year, int runTime) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runTime = runTime;
    }
}
