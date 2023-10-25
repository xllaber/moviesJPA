package com.llacerximo.movies.controller.model.actor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorDetailWeb {

    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;

}
