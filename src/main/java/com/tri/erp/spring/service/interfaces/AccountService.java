package com.tri.erp.spring.service.interfaces;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.json_param.SegmentParam;
import com.tri.erp.spring.model.Account;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface AccountService {
    public Account create(Account shop);
    public Account delete(int id);
    public List<AccountDTO> findAll();
    public Account update(Account account);
    public AccountDTO findById(int id);
    public Response processCreate(Account account, BindingResult bindingResult, MessageSource messageSource);
    public Response processUpdate(Account account, BindingResult bindingResult, MessageSource messageSource);
    public List<Account> findByTitle(String title);
    public List<Account> findByIdNotIn(Integer... accountId);
    public List<AccountDTO> findAllTree();
    public List<AccountDTO> findAllBySegment(String[] segmentIds);
}
