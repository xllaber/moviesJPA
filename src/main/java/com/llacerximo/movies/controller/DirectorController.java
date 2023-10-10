package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Director;
import com.llacerximo.movies.domain.service.DirectorService;
import com.llacerximo.movies.http_response.Response;
import com.llacerximo.movies.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Value("${default.page.size}")
    private Integer LIMIT;
    @Value("${default.page.num}")
    private Integer PAGE_NUM;

    @Autowired
    DirectorService directorService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Response getAll(@RequestParam Optional<Integer> page, @RequestParam(required = false) Optional<Integer> pageSize) {
        Integer pageSizeInput = pageSize.orElseGet(() -> LIMIT);
        Integer pageNum = page.orElseGet(() -> PAGE_NUM);
        return new Response(directorService.getAllPaginated(pageNum, pageSizeInput), new PaginationUtils(directorService.getTotalRecords(), pageSizeInput, pageNum));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Response insert(@RequestBody Director director){
        Integer id = directorService.insert(director);
        director.setId(id);
        return new Response(director);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Integer id, @RequestBody Director director) {
        director.setId(id);
        directorService.update(director);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        directorService.delete(id);
    }

}
