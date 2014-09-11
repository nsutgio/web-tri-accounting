package com.tri.erp.spring.commons;

import com.tri.erp.spring.commons.helpers.Client;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by TSI Admin on 9/11/2014.
 */

public class Debug {

    public static void printAndQuit(ArrayList<Object> objs)
    {
        String message = "";
        for (Object object : objs) {
            String val = String.valueOf(object);
            message += val + "\n";
        }
        System.out.println(message);
        System.exit(0);
    }

    public static void printAndQuit(Object obj)
    {
        System.out.println(String.valueOf(obj));
        System.exit(0);
    }

    public static void print(Object obj)
    {
        System.out.println(String.valueOf(obj));
    }
}