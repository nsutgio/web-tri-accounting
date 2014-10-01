<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<mytag:master>
    <jsp:attribute name="head">
        <link href="<c:url value="/resources/css/treeGrid/treeGrid.css" />" rel="stylesheet">
    </jsp:attribute>
    <jsp:attribute name="body">
        <div ng-app="account">
            <div id="coa-content" ng-view>Loading...</div>
        </div>
    </jsp:attribute>
</mytag:master>

<script src="<c:url value="/resources/js/app/routes/account-route.js" />"></script>
<script src="<c:url value="/resources/js/app/factories/account-factory.js" />"></script>
<script src="<c:url value="/resources/js/app/factories/business-segment-factory.js" />"></script>
<script src="<c:url value="/resources/js/app/services/error-handler-service.js" />"></script>
<script src="<c:url value="/resources/js/app/services/account-service.js" />"></script>
<script src="<c:url value="/resources/js/app/services/jquery-fn-wrapper-service.js" />"></script>
<script src="<c:url value="/resources/js/app/controllers/account-ctrl.js" />"></script>
<script src="<c:url value="/resources/js/app/directives/tree-grid-directive.js" />"></script>