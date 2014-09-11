package com.tri.erp.spring.validator;

import com.tri.erp.spring.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class AccountValidator implements Validator {
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
    }
}
