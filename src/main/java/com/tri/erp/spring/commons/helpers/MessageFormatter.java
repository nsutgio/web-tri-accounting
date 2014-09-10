package com.tri.erp.spring.commons.helpers;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.commons.beans.CreateAccountResponse;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by TSI Admin on 9/10/2014.
 */
public class MessageFormatter {

    public static Response errorMessages(BindingResult bindingResult, MessageSource messageSource, Response response) {
        // create service for this later
        for (Object object : bindingResult.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;

                /**
                 * Use null as second parameter if you do not use i18n
                 * (internationalization)
                 */
                String message = messageSource.getMessage(fieldError, null);
                response.getFields().add(fieldError.getField());
                response.getMessages().add(message);
            }
        }
        return response;
    }
}
