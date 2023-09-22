package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    DirectorService directorService;

    Director director = new Director("Joss Whedon", 1964, null);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/insert")
    public Director create(Director director){
        try {
            Integer id = directorService.create(this.director);
            this.director.setId(id);
            return this.director;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
