package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.service.ActorService;
import com.llacerximo.movies.http_response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    ActorService actorService;

    private Actor actor1 = new Actor("Robert Downey Jr.", 1964, null);
    private Actor actor2 = new Actor("Chris Evans", 1981, null);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Actor insert(@RequestBody Actor actor) {
        Integer id = actorService.insert(actor);
        actor.setId(id);
        return actor;
    }
}
