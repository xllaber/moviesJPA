package com.llacerximo.movies.http_errors;

public class ErrorMessage {
    private String message;
    private int code;

    public ErrorMessage (String message, int code){
        this.message = message;
        this.code = code;
    }
}
