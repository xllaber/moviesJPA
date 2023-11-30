package com.llacerximo.movies.controller.model.actor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorDetailWeb {

    private Integer id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;

}
