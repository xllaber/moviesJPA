package com.llacerximo.movies.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatonUtils {
    private Integer totalRecords;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private String next;
    private String previous;

    public PaginatonUtils(Integer totalRecords, Integer pageSize, Integer page) {
        this.totalRecords = totalRecords;
        this.page = page;
        this.pageSize = pageSize;
        int totalPages = (int) (Math.ceil((double) totalRecords / pageSize));
        this.totalPages = totalPages;

        if (page > 1 && totalPages > 1)
            this.previous = "/movies?page=" + (page - 1);
        if (page < totalPages)
            this.next = "/movies?page=" + (page + 1);
    }
}
