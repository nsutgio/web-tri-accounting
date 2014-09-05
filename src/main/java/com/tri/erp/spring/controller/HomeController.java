/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tri.erp.spring.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 *
 * @author TSI Admin
 */
@Controller
public class HomeController { 

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "admin/admin";
        } else {
            return "home";
        }
    }  
    
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin() {
        return "admin/admin";
    } 
    
    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String fourZeroThree() {
        return "403";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout() {
        return "redirect:login?logout";
    }
}
