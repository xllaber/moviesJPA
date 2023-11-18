package com.llacerximo.movies.controller.model.movie;

import lombok.Data;

import java.util.List;

@Data
public class MovieUpdateWeb {

    private Integer id;
    private String title;
    private Integer year;
    private Integer runtime;
    private Integer directorId;
    private List<Integer> characterIds;

}
