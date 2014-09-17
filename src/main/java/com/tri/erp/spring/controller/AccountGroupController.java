package com.tri.erp.spring.controller;

import com.tri.erp.spring.commons.Response;
import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.model.Account;
import com.tri.erp.spring.model.AccountGroup;
import com.tri.erp.spring.service.interfaces.AccountGroupService;
import com.tri.erp.spring.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by TSI Admin on 9/16/2014.
 */

@Controller
@RequestMapping("/account/group")
public class AccountGroupController {

    @Autowired
    AccountGroupService accountGroupService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountGroup> getAccountGroups() {
        return accountGroupService.findAll();
    }

}
