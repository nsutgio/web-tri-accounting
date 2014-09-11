package com.tri.erp.spring.commons.helpers;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.commons.beans.CreateAccountResponse;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;

/**
 * Created by TSI Admin on 9/10/2014.
 */
public class MessageFormatter {

    private MessageSource messageSource;
    private BindingResult bindingResult;
    private Response response;

    public MessageFormatter(Response response) {
        this.response = response;
    }
    public MessageFormatter(BindingResult bindingResult, MessageSource messageSource, Response response) {
        this.bindingResult = bindingResult;
        this.messageSource = messageSource;
        this.response = response;
    }

    public void buildErrorMessages() {
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
    }

    public void setCustomMessage(String[]... keyFields) {
        if (keyFields.length > 0) {
            for (String[] keyField : keyFields) {
                String newKey = keyField[0];
                String message = keyField[1];

                response.getMessages().add(message);
                response.getFields().add(newKey);
            }
        }
    }

    public Response getRespone() {
        return this.response;
    }
}
