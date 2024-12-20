package br.com.walkito.simulol.utils;

import br.com.walkito.simulol.models.enums.ErroMessages;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;

public class Utils {

    public static boolean emailValidation(String email){
        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(email);
    }

    public static DefaultResponse getDefaultErrorResponse(Exception e){
        DefaultResponse response = new DefaultResponse();

        response.setMessage(ErroMessages.INTERNAL_SERVER_ERROR.getMensagemErro());
        response.setObject(null);
        response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        e.printStackTrace();

        return response;
    }
}
