package com.llacerximo.movies.utils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class PaginatonUtils {
    @JsonProperty("Total Records")
    private Integer totalRecords;
    @JsonProperty("Page")
    private Integer page;
    @JsonProperty("Page Size")
    private Integer pageSize;
    @JsonProperty("Total Pages")
    private Integer totalPages;
    @JsonProperty("Next page")
    private String next;
    @JsonProperty("Previous page")
    private String previous;

    public PaginatonUtils(Integer totalRecords, Integer pageSize, Integer page) {
        this.totalRecords = totalRecords;
        this.page = page;
        this.pageSize = pageSize;
        int totalPages = (int) (Math.ceil((double) totalRecords / pageSize));
        this.totalPages = totalPages;

        if (page > 1 && totalPages > 1)
            this.previous = "http://localhost:8080/movies?page=" + (page - 1);
        if (page < totalPages)
            this.next = "http://localhost:8080/movies?page=" + (page + 1);
    }

    public PaginatonUtils(Integer totalRecords){
        this.totalRecords = totalRecords;
    }

    public PaginatonUtils buildPagination(Integer totalRecords, Integer pageSize, Optional<Integer> page){
        return page.map(pageNum -> new PaginatonUtils(totalRecords, pageSize, pageNum)).orElseGet(() -> new PaginatonUtils(totalRecords));
    }
}
