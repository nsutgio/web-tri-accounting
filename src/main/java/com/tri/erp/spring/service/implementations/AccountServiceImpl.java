package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.repo.AccountRepo;
import com.tri.erp.spring.service.interfaces.AccountService;
import org.springframework.stereotype.Service;

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


    @Override
    public Account create(Account shop) {
        return null;
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
}
