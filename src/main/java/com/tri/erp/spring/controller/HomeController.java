/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tri.erp.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 

/**
 *
 * @author TSI Admin
 */
@Controller
public class HomeController { 

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index() {
        return "home";
    }  
    
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin() {
        return "admin";
    } 
    
    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String fourZeroThree() {
        return "403";
    } 
}
