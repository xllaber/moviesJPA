package com.llacerximo.movies.domain.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Director {

    private String name;
    private Integer  birthYear;
    private Integer  deathYear;
    private Integer id;

    public Director(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }
}
