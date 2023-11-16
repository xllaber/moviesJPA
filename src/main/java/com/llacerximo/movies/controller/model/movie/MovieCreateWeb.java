package com.llacerximo.movies.controller.model.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MovieCreateWeb {

    private String title;
    private int year;
    private int runtime;
    private int directorId;
    private Map<Integer, String> characters;

}
