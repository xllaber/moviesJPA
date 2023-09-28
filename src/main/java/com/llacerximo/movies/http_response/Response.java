
package com.llacerximo.movies.http_response;

import com.llacerximo.movies.utils.PaginatonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Response {

    private Object data;
    private PaginatonUtils pagination;

    public Response(Object data, PaginatonUtils pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public Response(Object data, Integer totalRecords){
        this.data = data;

    }


}
