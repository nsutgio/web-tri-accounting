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
import com.tri.erp.spring.validator.AccountValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final String INDENTION = "&nbsp;&nbsp;";
    private List<AccountDTO> accountsDtoList = new ArrayList<>();

    @Resource
    private AccountRepo accountRepo;

    @Resource
    private AccountTypeRepo accountTypeRepo;

    @Resource
    private AccountGroupRepo accountGroupRepo;

    @PersistenceContext
    private EntityManager em;

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

        List<Account> topLevelAccounts = findByParentAccountIdOrderByCodeAsc(0);// top level accounts
        for(Account account : topLevelAccounts) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setCode(account.getCode());
            accountDTO.setId(account.getId());
            accountDTO.setTitle(account.getTitle());
            if (account.getAccountType() != null) {
                accountDTO.setAccountType(account.getAccountType());
            }
            this.accountsDtoList.add(accountDTO);

//            findDescendants(account, this.INDENTION);
        }
        return this.accountsDtoList;
    }

    @Override
    public Account update(Account account) {
        return accountRepo.save(account);
    }

    @Override
    public AccountDTO findById(int id) {
        Account account = accountRepo.findOne(id);

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId(account.getId());
        accountDTO.setTitle(account.getTitle());
        accountDTO.setCode(account.getCode());
        if (account.getAccountType() != null) {
            accountDTO.setAccountType(account.getAccountType());
        }
        accountDTO.setLevel(account.getLevel());
        accountDTO.setAuxAccount(account.getAuxiliaryAccount());
        accountDTO.setGLAccount(account.getGLAccount());
        if (account.getAccountGroup() != null) {
            accountDTO.setAccountGroup(account.getAccountGroup());
        }
        accountDTO.hasSL(account.hasSL());
        accountDTO.isActive(account.isActive());

        Account parentAccount = accountRepo.findOne(account.getParentAccountId());
        if (parentAccount != null) {
            accountDTO.setParentAccount(parentAccount.getTitle());
            accountDTO.setParentAccountId(parentAccount.getId());
        }
        accountDTO.setSLAccount(account.getSLAccount());
        accountDTO.setNormalBalance(account.getNormalBalance());
        accountDTO.setIsHeader(account.getIsHeader());

        return accountDTO;
    }

    @Transactional
    public Response processCreate(Account account, BindingResult bindingResult, MessageSource messageSource) {
        Response response = new CreateAccountResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        AccountValidator accountValidator = new AccountValidator();
        accountValidator.setService(this);
        accountValidator.validate(account, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
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
            if (account.getId() > 0) {  // update mode
                update(account);
            } else {
                account = create(account);
            }

            response.setModelId(account.getId());
            response.setSuccessMessage("Account successfully saved!");
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    public List<Account> findByTitle(String title) {
        return accountRepo.findByTitle(title);
    }

    @Override
    public List<Account> findByParentAccountIdOrderByCodeAsc(Integer accountId) {
        return accountRepo.findByParentAccountIdOrderByCodeAsc(accountId);
    }

    public Response processUpdate(Account account, BindingResult bindingResult, MessageSource messageSource) {
        return processCreate(account, bindingResult, messageSource);
    }

    private void findDescendants(Account currentAccount, String indention) {
        List<Account> children = accountRepo.findByParentAccountIdOrderByCodeAsc(currentAccount.getId());

        for (Account childAccount : children) {
            AccountDTO accountDTO = new AccountDTO();

            childAccount.setCode(indention + childAccount.getCode());
            childAccount.setTitle(indention + childAccount.getTitle());

            accountDTO.setCode(childAccount.getCode());
            accountDTO.setId(childAccount.getId());
            accountDTO.setTitle(childAccount.getTitle());

            if (childAccount.getAccountType() != null) {
                accountDTO.setAccountType(childAccount.getAccountType());
            }

            this.accountsDtoList.add(accountDTO);
            findDescendants(childAccount, indention + INDENTION);
        }
    }
}
