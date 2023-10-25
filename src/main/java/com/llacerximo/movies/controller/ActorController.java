package com.llacerximo.movies.controller;

import com.llacerximo.movies.controller.model.actor.ActorListWeb;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.service.ActorService;
import com.llacerximo.movies.http_response.Response;
import com.llacerximo.movies.mapper.ActorMapper;
import com.llacerximo.movies.mapper.DirectorMapper;
import com.llacerximo.movies.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    ActorService actorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Response getAll(@RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return  new Response(
            actorService.getAllPaginated(page, pageSize)
                    .stream()
                    .map(ActorMapper.mapper::toActorListWeb)
                    .toList(),
            new PaginationUtils(actorService.getTotalRecords(), page, pageSize)
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response getById(@PathVariable("id") Integer id){
        return new Response(ActorMapper.mapper.toActorDetailWeb(actorService.getById(id)));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Response insert(@RequestBody Actor actor) {
        Integer id = actorService.insert(actor);
        actor.setId(id);
        return new Response(actor);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Integer id, @RequestBody Actor actor) {
        actor.setId(id);
        actorService.update(actor);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        actorService.delete(id);
    }
}
