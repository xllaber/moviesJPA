package com.llacerximo.movies.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PaginationUtils {

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

    public void setNextAndPrevious(Integer totalRecords, Integer page) {
        int totalPages = (int) (Math.ceil((double) totalRecords / pageSize));
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())). getRequest();
        String url = request.getRequestURL().toString();
        if (page > 1 && totalPages > 1)
            this.previous = url +  "?page=" + (page - 1);
        if (page < totalPages)
            this.next = url +  "?page=" + (page + 1);
    }

}
