package com.tri.erp.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by TSI Admin on 9/18/2014.
 */

@Controller
@RequestMapping("/common")
public class CommonViewController {

    @RequestMapping("/account-browser")
    public String getAccountBrowser() {
        return "common/account-browser";
    }
}
