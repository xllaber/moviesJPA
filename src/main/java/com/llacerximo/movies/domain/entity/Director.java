package com.llacerximo.movies.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Director {

    private String name;
    private Integer  birthYear;
    private Integer  deathYear;
}
