package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.entity.Actor;
import com.llacerximo.movies.domain.service.ActorService;
import com.llacerximo.movies.http_response.Response;
import com.llacerximo.movies.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Value("${default.page.size}")
    private Integer LIMIT;

    @Autowired
    ActorService actorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Response getAll(@RequestParam Optional<Integer> page, @RequestParam(required = false) Optional<Integer> pageSize) {
        if (page.isPresent()){
            Integer pageSizeInput = LIMIT;
            if (pageSize.isPresent())
                pageSizeInput = pageSize.get();

            return new Response(actorService.getAllPaginated(page, pageSizeInput), new PaginationUtils(actorService.getTotalRecords(), pageSizeInput, page.get()));
        }

        return new Response(actorService.getAll(), new PaginationUtils(actorService.getTotalRecords()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Actor insert(@RequestBody Actor actor) {
        Integer id = actorService.insert(actor);
        actor.setId(id);
        return actor;
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
