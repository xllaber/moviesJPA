
package com.llacerximo.movies.http_response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.llacerximo.movies.utils.PaginationUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"pagination", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private Object data;
    private PaginationUtils pagination;

    public Response(Object data, PaginationUtils pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public Response(Object data){
        this.data = data;
    }

}
