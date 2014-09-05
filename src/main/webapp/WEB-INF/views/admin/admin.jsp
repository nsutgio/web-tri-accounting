<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.authority.SimpleGrantedAuthority" %>
<%@ page import="java.util.Collection" %>
<%--
    Document   : admin
    Created on : 06 26, 14, 11:55:53 AM
    Author     : TSI Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head>
    <body>
        <h1>This is an admin page inner</h1>
        Roles:
        <%
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            out.println(authorities.toArray()[0].toString());
        %>
    </body>
</html>
