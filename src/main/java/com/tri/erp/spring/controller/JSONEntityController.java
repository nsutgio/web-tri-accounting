/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tri.erp.spring.controller;

import com.tri.erp.spring.dto.AccountDTO;
import com.tri.erp.spring.model.AccountGroup;
import com.tri.erp.spring.model.AccountType;
import com.tri.erp.spring.model.BusinessSegment;
import com.tri.erp.spring.service.interfaces.AccountGroupService;
import com.tri.erp.spring.service.interfaces.AccountService;
import com.tri.erp.spring.service.interfaces.AccountTypeService;
import com.tri.erp.spring.service.interfaces.BusinessSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author TSI Admin
 */

@Controller
@RequestMapping("/json")
public class JSONEntityController {

    @Autowired
    BusinessSegmentService businessSegmentService;

    @Autowired
    AccountTypeService accountTypeService;

    @Autowired
    AccountGroupService accountGroupService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/bus-seg", method = RequestMethod.GET)
    @ResponseBody
    public List<BusinessSegment> getBusinessSegments() {
        return businessSegmentService.findAll();
    }

    @RequestMapping(value = "/account-groups", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountGroup> getAccountGroups() {
        return accountGroupService.findAll();
    }

    @RequestMapping(value = "/account-types", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountType> getAccountTypes() {
        return accountTypeService.findAll();
    }


    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountDTO> accountList() {
        List<AccountDTO> accountList = accountService.findAll();
        return accountList;
    }

}
