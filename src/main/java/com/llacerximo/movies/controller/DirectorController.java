package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectorController {
    @Autowired
    DirectorService directorService;

    Director director = new Director("Joss Whedon", 1964, null);

    @GetMapping("/insert")
    public void create(){
        try {
            directorService.create(this.director);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
