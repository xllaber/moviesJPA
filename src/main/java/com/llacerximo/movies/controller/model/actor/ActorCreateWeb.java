package com.llacerximo.movies.controller.model.actor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorCreateWeb {

    private String name;
    private Integer birthYear;
    private Integer deathYear;

}
