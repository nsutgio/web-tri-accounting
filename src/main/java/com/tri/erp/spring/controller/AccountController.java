package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.json_param.SegmentParam;
import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 9/16/2014.
 */

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AccountDTO getAccount(@PathVariable Integer id) {
        AccountDTO account = accountService.findById(id);
        return account;
    }

    @RequestMapping(value = "/{id}/except", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> getAccountsExcept(@PathVariable Integer id) {
        return accountService.findByIdNotIn(id);
    }

    // create account
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response createAccount(@Valid @RequestBody Account account, BindingResult bindingResult) {
        return accountService.processCreate(account, bindingResult, messageSource);
    }

    // update account
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Response updateAccount(@Valid @RequestBody Account account, BindingResult bindingResult) {
        return accountService.processUpdate(account, bindingResult, messageSource);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDTO> accountList() {
        List<AccountDTO> accountList = accountService.findAll();
        return accountList;
    }

    @RequestMapping(value = "/list/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDTO> accountListTree() {
        List<AccountDTO> accountList = accountService.findAllTree();
        return accountList;
    }

    @RequestMapping(value = "/by-segment", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDTO> getAccountBySegments(@RequestParam(value = "segmentIds") String[] segmentIds) {
        return accountService.findAllBySegment(segmentIds);
    }
}
