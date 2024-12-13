package br.com.walkito.simulol.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class Utils {

    public static boolean emailValidation(String email){
        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(email);
    }
}
