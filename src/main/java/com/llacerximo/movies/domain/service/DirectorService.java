package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.controller.model.director.DirectorListWeb;
import com.llacerximo.movies.domain.entity.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorService {

    List<Director> getAllPaginated(Integer page, Integer pageSizeInput);


    Director getById(Integer id);

    Integer insert(Director director);

    void update(Director director);

    void delete(Integer id);

    Integer getTotalRecords();

}
