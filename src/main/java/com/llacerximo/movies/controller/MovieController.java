package com.llacerximo.movies.controller;

import com.llacerximo.movies.domain.entity.Movie;
import com.llacerximo.movies.domain.service.MovieService;
import com.llacerximo.movies.http_response.Response;
import com.llacerximo.movies.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Value("${default.page.size}")
    private Integer LIMIT;
    @Value("${default.page.num}")
    private Integer PAGE_NUM;

    @Autowired
    MovieService movieService;

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("")
//    public Response getAll(@RequestParam Optional<Integer> page, @RequestParam(required = false) Optional<Integer> pageSize) {
//        if (page.isPresent()){
//            Integer pageSizeInput = LIMIT;
//            if (pageSize.isPresent())
//                pageSizeInput = pageSize.get();
//
//            return new Response(movieService.getAllPaginated(page, pageSizeInput), new PaginationUtils(movieService.getTotalRecords(), pageSizeInput, page.get()));
//        }
//
//        return new Response(movieService.getAll(), new PaginationUtils(movieService.getTotalRecords()));
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Response getAll(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> pageSize) {
        Integer pageSizeInput = pageSize.orElseGet(() -> LIMIT);
        Integer pageNum = page.orElseGet(() -> PAGE_NUM);
        return new Response(movieService.getAllPaginated(pageNum, pageSizeInput), new PaginationUtils(movieService.getTotalRecords(), pageSizeInput, pageNum));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response find(@PathVariable("id") int id) {
        return new Response(movieService.findById(id));
    }


}
