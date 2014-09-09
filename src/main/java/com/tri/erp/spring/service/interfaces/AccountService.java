package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.model.Account;

import java.util.List;

public interface AccountService {
    public Account create(Account shop);
    public Account delete(int id);
    public List<AccountDTO> findAll();
    public Account update(Account shop);
    public Account findById(int id);
}
