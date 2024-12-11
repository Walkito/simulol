package br.com.walkito.simulol.utils;

import org.springframework.http.HttpStatusCode;

public class DefaultResponse {
    private String message;
    private Object object;
    private HttpStatusCode httpStatusCode;

    public DefaultResponse(){

    }

    public DefaultResponse(String message, Object object, HttpStatusCode httpStatusCode) {
        this.message = message;
        this.object = object;
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
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
