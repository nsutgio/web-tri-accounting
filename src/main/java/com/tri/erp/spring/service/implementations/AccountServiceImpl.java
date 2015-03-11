package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.commons.beans.CreateAccountResponse;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.StringFormatter;
import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.json_param.SegmentParam;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.service.interfaces.AccountService;
import com.tri.erp.spring.validator.AccountValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Service
public class AccountServiceImpl implements AccountService {
    private List<AccountDTO> accountsDtoList;

    @Resource
    private AccountRepo accountRepo;

    @Resource
    private AccountTypeRepo accountTypeRepo;

    @Resource
    private AccountGroupRepo accountGroupRepo;

    @Resource
    private SegmentAccountRepo segmentAccountRepo;

    @Resource
    private BusinessSegmentRepo businessSegmentRepo;

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
    @Transactional(readOnly = true)
    public List<AccountDTO> findAll() {
        this.accountsDtoList = new ArrayList<>();

        List<Account> accountList = accountRepo.findAllByOrderByTitleAsc();

        for(Account account : accountList) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setCode(account.getCode());
            accountDTO.setParentAccountId(account.getParentAccountId());
            accountDTO.setId(account.getId());
            accountDTO.setTitle(account.getTitle());
            accountDTO.setLevel(account.getLevel());
            if (account.getAccountType() != null) {
                accountDTO.setAccountType(account.getAccountType());
            }
            accountsDtoList.add(accountDTO);
        }
        return accountsDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAllTree() {
        this.accountsDtoList = new ArrayList<>();

        List<Account> topLevelAccounts = accountRepo.findByParentAccountIdOrderByCodeAsc(0);// top level accounts
        for(Account account : topLevelAccounts) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setCode(account.getCode());
            accountDTO.setId(account.getId());
            accountDTO.setTitle(account.getTitle());
            if (account.getAccountType() != null) {
                accountDTO.setAccountType(account.getAccountType());
            }
            this.accountsDtoList.add(accountDTO);
            findDescendants(account);
        }
        return this.accountsDtoList;
    }

    @Override
    public List<AccountDTO> findAllBySegment(String[] segmentIds) {
        List<AccountDTO> list = new ArrayList<>();
        if (segmentIds != null && segmentIds.length > 0) {
            List<Object[]> result = accountRepo.findBySegmentIds(Arrays.asList(segmentIds));
            if (result != null) {
                for(Object[] objects : result) {
                    AccountDTO a = new AccountDTO();
                    a.setId((Integer) objects[0]);
                    a.setTitle((String) objects[1]);
                    a.setCode((String) objects[2]);

                    AccountType at = new AccountType((String) objects[3], null);
                    a.setAccountType(at);

                    list.add(a);
                }
            }
        }
        return list;
    }

    @Override
    public Account update(Account account) {
        return accountRepo.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO findById(int id) {
        AccountDTO accountDTO = new AccountDTO();

        Account account = accountRepo.findOne(id);
        if (account != null) {
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
                accountDTO.setParentAccount(parentAccount);
            }

            List<SegmentAccount> segmentAccounts = segmentAccountRepo.findByAccountId(account.getId());

            accountDTO.setSegmentAccounts(segmentAccounts);
            accountDTO.setSLAccount(account.getSLAccount());
            accountDTO.setNormalBalance(account.getNormalBalance());
            accountDTO.setIsHeader(account.getIsHeader());
        }
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

            if (account.getParentAccount() != null) {
                Account parentAccount = accountRepo.findOne(account.getParentAccount().getId());
                if (parentAccount != null && parentAccount instanceof Account) {
                    account.setLevel(parentAccount.getLevel() + 1);
                }
            } else {
                account.setParentAccountId(0);
            }

            if (account.getId() > 0) {  // update mode
                update(account);

                if(!Checker.collectionIsEmpty(account.getSegmentAccounts())) {
                    List<SegmentAccount> segmentAccountList = segmentAccountRepo.findByAccountId(account.getId());
                    if (Checker.collectionIsEmpty(segmentAccountList)) {
                        // fresh insert
                        persistSegmentAccounts(account);
                    } else { // add up
                        Set<SegmentAccount> segmentAccounts = account.getSegmentAccounts();
                        for(SegmentAccount segmentAccount : segmentAccounts) {
                            BusinessSegment businessSegment = businessSegmentRepo.findOne(segmentAccount.getBusinessSegment().getId());
                            String code = generateSegmentAccountCode(businessSegment, account);
                            segmentAccountRepo.saveWithExistenceCheck(businessSegment.getId(), account.getId(), code);
                        }
                    }
                }
            } else {
                account = create(account);
                persistSegmentAccounts(account);
            }

            response.setModelId(account.getId());
            response.setSuccessMessage("Account successfully saved!");
            response.setSuccess(true);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findByTitle(String title) {
        return accountRepo.findByTitle(title);
    }

    @Override
    public List<Account> findByIdNotIn(Integer... accountId) {
        return accountRepo.findByIdNotInOrderByTitleAsc(accountId);
    }

    public Response processUpdate(Account account, BindingResult bindingResult, MessageSource messageSource) {
        return processCreate(account, bindingResult, messageSource);
    }

    private String generateSegmentAccountCode(BusinessSegment businessSegment, Account account) {
        return businessSegment.getBusinessActivity().getCode() + businessSegment.getCode() + "-" + account.getCode();
    }

    private void persistSegmentAccounts(Account account) {
        if (!Checker.collectionIsEmpty(account.getSegmentAccounts())) {
            Set<SegmentAccount> segmentAccounts = account.getSegmentAccounts();
            for(SegmentAccount segmentAccount : segmentAccounts) {
                BusinessSegment businessSegment = businessSegmentRepo.findOne(segmentAccount.getBusinessSegment().getId());
                String code = generateSegmentAccountCode(businessSegment, account);
                segmentAccount.setAccount(account);
                segmentAccount.setAccountCode(code);
                segmentAccountRepo.save(segmentAccount);
            }
        }
    }

    private void findDescendants(Account currentAccount) {
        List<Account> children = accountRepo.findByParentAccountIdOrderByCodeAsc(currentAccount.getId());

        for (Account childAccount : children) {
            AccountDTO accountDTO = new AccountDTO();

            accountDTO.setCode(childAccount.getCode());
            accountDTO.setId(childAccount.getId());
            accountDTO.setTitle(childAccount.getTitle());
            accountDTO.setParentAccountId(childAccount.getParentAccountId());

            if (childAccount.getAccountType() != null) {
                accountDTO.setAccountType(childAccount.getAccountType());
            }

            this.accountsDtoList.add(accountDTO);
            findDescendants(childAccount);
        }
    }
}
