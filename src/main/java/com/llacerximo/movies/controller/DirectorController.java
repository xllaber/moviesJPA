package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    DirectorService directorService;

    Director director = new Director("Joss Whedon", 1964, null);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Director insert(@RequestBody Director director){
        Integer id = directorService.insert(director);
        director.setId(id);
        return director;
    }
}
