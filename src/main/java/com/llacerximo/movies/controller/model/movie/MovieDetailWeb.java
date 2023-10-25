package com.llacerximo.movies.controller.model.movie;

import com.llacerximo.movies.controller.model.actor.ActorListWeb;
import com.llacerximo.movies.controller.model.director.DirectorListWeb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MovieDetailWeb {

    private Integer id;
    private String title;
    private Integer year;
    private Integer runtime;
    private DirectorListWeb director;
    private List<ActorListWeb> actors;

}
