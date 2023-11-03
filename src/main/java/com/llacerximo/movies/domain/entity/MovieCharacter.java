package com.llacerximo.movies.domain.entity;

import lombok.*;

@Data
public class MovieCharacter {

    private Integer id;
    private String name;
//    private Movie movie;
    private Actor actor;

}
