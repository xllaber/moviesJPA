package com.llacerximo.movies.domain.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Actor {

    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private Integer id;

    public Actor(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

}
