package com.tri.erp.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TSI Admin on 9/9/2014.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return getAdminHomeView();
    }

    @RequestMapping(value = {"/dashboard"}, method = RequestMethod.GET)
    public String dashboard() {
        return getAdminHomeView();
    }


    private String getAdminHomeView() {
        return "admin/dashboard";
    }
}
