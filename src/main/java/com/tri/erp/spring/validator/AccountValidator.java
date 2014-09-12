package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.repo.AccountRepo;
import com.tri.erp.spring.service.implementations.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javax.swing.*;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class AccountValidator implements Validator {

    private AccountServiceImpl accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account account = (Account) o;

        if (account.getAccountGroup() == null || account.getAccountGroup().getId() <= 0) {
            errors.rejectValue("accountGroup", null, "Must select account group");
        }

        if (account.getAccountType() == null || account.getAccountType().getId() <= 0) {
            errors.rejectValue("accountType", null, "Must select account type");
        }

        Account a = accountService.findByTitle(account.getTitle());
        if (a != null) {
            FieldError titleError = errors.getFieldError("title");
            if (titleError != null) {
//                String message = titleError.getDefaultMessage();
//                message += "<br/>" + newMessage;
                errors.rejectValue("title", "account.name.duplicate");
            } else {
                errors.rejectValue("title", "account.name.duplicate");
            }
        }
    }

    public void setService(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }
}
