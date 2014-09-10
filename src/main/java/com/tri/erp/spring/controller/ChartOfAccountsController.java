package com.tri.erp.spring.controller;

import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Controller
@RequestMapping("/admin/coa")
public class ChartOfAccountsController {
    private final String BASE_PATH = "admin/coa/partials/";

    @Autowired
    AccountService accountService;

    // view providers
    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "admin/coa/main";
    }

    @RequestMapping(value = "/accounts-tree-page", method = RequestMethod.GET)
    public String accountsTree() {
        return BASE_PATH + "chart-of-accounts";
    }

    @RequestMapping(value = "/new-account-page", method = RequestMethod.GET)
    public String newAccount() {
        return BASE_PATH + "add-edit";
    }

    // end: view providers

    // json providers
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDTO> accountList() {
        List<AccountDTO> accountList = accountService.findAll();
        return accountList;
    }
}
