package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.repo.AccountRepo;
import com.tri.erp.spring.service.interfaces.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Account> findAll() {
        return accountRepo.findAll();
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
