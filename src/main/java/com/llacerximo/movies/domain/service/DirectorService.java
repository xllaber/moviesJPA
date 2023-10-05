package com.llacerximo.movies.domain.service;

import com.llacerximo.movies.domain.entity.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorService {
    List<Director> getAllPaginated(Optional<Integer> page, Integer pageSizeInput);

    List<Director> getAll();

    Integer insert(Director director);

    void update(Director director);

    void delete(Integer id);

    Integer getTotalRecords();
}
