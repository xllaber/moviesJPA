package com.llacerximo.movies.domain.entity;

import lombok.*;

@Data
@NoArgsConstructor
public class MovieCharacter {

    private Integer id;
    private String characters;
    private Actor actor;

}
