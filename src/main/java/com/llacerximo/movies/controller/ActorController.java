package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    ActorService actorService;

    private Actor actor1 = new Actor("Robert Downey Jr.", 1964, null);
    private Actor actor2 = new Actor("Chris Evans", 1981, null);
    @GetMapping("/insert")
    public void insert() {
        try {
            actorService.insert(actor1);
            actorService.insert(actor2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
