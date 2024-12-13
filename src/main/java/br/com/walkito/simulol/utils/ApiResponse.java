package br.com.walkito.simulol.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ApiResponse extends ResponseEntity {

    public ApiResponse(DefaultResponse response){
        super(response, HttpStatusCode.valueOf(response.getHttpStatusCode()));
    }
}
