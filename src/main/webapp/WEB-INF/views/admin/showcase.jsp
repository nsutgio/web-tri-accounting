<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<mytag:master>
    <jsp:attribute name="head">
        <%-- includes here --%>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div ng-app>

            <p>{{ 1 + 1 }}</p>

            <div ng-controlelr="showCaseCtrl">
                <button title="browse account" type="button" class="btn btn-primary" ng-click="showAccountBrowserWithSegment()">Account with Segment</button>
                <div ng-include="accountBrowserWithSegmentUrl" class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"></div>
            </div>
        </div>
    </jsp:attribute>
</mytag:master>

<script type="text/javascript">
    var showcaseApp = angular.module('showcaseApp', []);

    showcaseApp.controller('showCaseCtrl', function ($scope) {
        $scope.accountBrowserWithSegmentUrl = "/common/account-browser-with-segment";
        $scope.showAccountBrowserWithSegment = function() {
            alert();
//            modalToggler.show('myModal2');
        }
    });
</script>