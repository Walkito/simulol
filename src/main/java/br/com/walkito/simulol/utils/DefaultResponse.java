package br.com.walkito.simulol.utils;

import org.springframework.http.HttpStatusCode;

public class DefaultResponse {
    private String message;
    private Object object;
    private int httpStatusCode;

    public DefaultResponse(String message, int httpStatusCode, Object object) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
        this.object = object;
    }

    public DefaultResponse(){

    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }
}
