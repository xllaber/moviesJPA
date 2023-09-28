
package com.llacerximo.movies.http_response;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Response {

    private Object data;
    private Integer totalRecords;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private String next;
    private String previous;

    public Response(Object data, Integer totalRecords, Optional<Integer> page, Integer pageSize) {
        this.data = data;
        this.totalRecords = totalRecords;
        page.ifPresent(pageNum -> buildPaginationMetaData(totalRecords, pageSize, pageNum));
    }

    private void buildPaginationMetaData(Integer totalRecords, Integer pageSize, Integer page) {
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
