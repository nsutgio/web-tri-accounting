<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<mytag:master>
    <jsp:attribute name="head">
    </jsp:attribute>
    <jsp:attribute name="body">
        <input type="button" class="btn btn-primary" id="create" value="Create" />
        <input type="button" class="btn btn-primary" id="create" value="Update" />
        <input type="button" class="btn btn-primary" id="create" value="Send for audit" />
        <hr/>
        <table id="request" class="table">
            <thead>
            <tr>
                <th>Column 1</th>
                <th>Column 2</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Row 1 Data 1</td>
                <td>Row 1 Data 2</td>
            </tr>
            <tr>
                <td>Row 2 Data 1</td>
                <td>Row 2 Data 2</td>
            </tr>
            </tbody>
        </table>
    </jsp:attribute>
</mytag:master>