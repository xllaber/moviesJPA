
package com.llacerximo.movies.http_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.llacerximo.movies.utils.PaginationUtils;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"totalRecords", "pagination", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private Object data;
    private PaginationUtils paginationOld;
    private Integer totalRecords;
    private Map<String, Object> pagination;

    public Response(Object data, PaginationUtils paginationOld) {
        this.data = data;
        this.paginationOld = paginationOld;
    }

    public void paginate(Integer page, Integer pageSize, String url) {
        this.pagination = new HashMap<>();
        this.pagination.put("page", page);
        this.pagination.put("page size", pageSize);
        int totalPages = (int) (Math.ceil((double) totalRecords / pageSize));
        this.pagination.put("total pages", totalPages);
        if (page > 1 && totalPages > 1)
            this.pagination.put("previous", url + "/movies?page=" + (page - 1));
        if (page < totalPages)
            this.pagination.put("previous", url + "/movies?page=" + (page + 1));
    }

    public Response(Object data){
        this.data = data;
    }

}
