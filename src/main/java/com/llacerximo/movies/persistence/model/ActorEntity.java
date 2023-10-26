package com.llacerximo.movies.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorEntity {

    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;

}
