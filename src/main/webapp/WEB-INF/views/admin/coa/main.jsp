<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<mytag:master>
    <jsp:attribute name="head">
        <%-- includes here --%>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div ng-app="coaApp">
            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <a href="#/new">Add</a>
                </div>
            </div>
            <div id="coa-content" ng-view>Loading...</div>
        </div>
    </jsp:attribute>
</mytag:master>

<script src="<c:url value="/resources/js/app/chart-of-accounts.js" />"></script>
<script src="<c:url value="/resources/js/controllers/chart-of-accounts.js" />"></script>