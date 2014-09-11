package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.commons.beans.CreateAccountResponse;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.StringFormatter;
import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.model.AccountGroup;
import com.tri.erp.spring.model.AccountType;
import com.tri.erp.spring.repo.AccountGroupRepo;
import com.tri.erp.spring.repo.AccountRepo;
import com.tri.erp.spring.repo.AccountTypeRepo;
import com.tri.erp.spring.service.interfaces.AccountService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountRepo accountRepo;

    @Resource
    private AccountTypeRepo accountTypeRepo;

    @Resource
    private AccountGroupRepo accountGroupRepo;

    @Override
    public Account create(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account delete(int id) {
        return null;
    }

    @Override
    public List<AccountDTO> findAll() {
        List<AccountDTO> accountsDtoList = new ArrayList<>();
        List<Account> accounts = accountRepo.findAll();
        if (accounts != null && accounts.size() > 0) {
            for (Account account : accounts) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setCode(account.getCode());
                accountDTO.setId(account.getId());
                accountDTO.setTitle(account.getTitle());
                accountsDtoList.add(accountDTO);
            }
        }
        return accountsDtoList;
    }

    @Override
    public Account update(Account shop) {
        return null;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    @Transactional
    public Response processCreate(Account account, BindingResult bindingResult, MessageSource messageSource) {
        Response response = new CreateAccountResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);
        boolean hasError = false;

        if (account.getAccountGroup() == null || account.getAccountGroup().getId() <= 0) {
            messageFormatter.setCustomMessage(new String[] {"accountGroup", "Must select an account group"});
            hasError = true;
        }

        if (account.getAccountType() == null || account.getAccountType().getId() <= 0) {
            messageFormatter.setCustomMessage(new String[] {"accountType", "Must select an account type"});
            hasError = true;
        }

        if (bindingResult.hasErrors() || hasError) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getRespone();
            response.setSuccess(false);
        } else {

            AccountType accountType = accountTypeRepo.findOne(account.getAccountType().getId());
            AccountGroup accountGroup = accountGroupRepo.findOne(account.getAccountGroup().getId());

            account.setTitle(StringFormatter.ucFirst(account.getTitle()));
            account.setCode(StringFormatter
                    .buildAccountCode(
                            accountType.getCode(),
                            accountGroup.getAccountGroupCode(),
                            account.getGLAccount(),
                            account.getSLAccount(),
                            account.getAuxiliaryAccount()
                    )
            );

            if (account.getParentAccountId() > 0) {
                Account parentAccount = accountRepo.findOne(account.getParentAccountId());
                if (parentAccount != null && parentAccount instanceof Account) {
                    account.setLevel(parentAccount.getLevel() + 1);
                }
            }

            create(account);
            response.setSuccessMessage("Account successfully saved!");
            response.setSuccess(true);
        }
        return response;
    }
}
