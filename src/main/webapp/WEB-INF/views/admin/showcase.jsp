<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<mytag:master>
    <jsp:attribute name="head">
        <%-- includes here --%>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div ng-app="showcaseApp">
            <div ng-controller="accountBrowserCtrl">
                <button title="browse account" type="button" class="btn btn-primary" ng-click="showAccountBrowserWithSegment()">Account with Segment</button>
                <div ng-include="accountBrowserWithSegmentUrl" class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"></div>
            </div>
        </div>
    </jsp:attribute>
</mytag:master>

<script src="<c:url value="/resources/js/app/factories/business-segment-factory.js" />"></script>
<script src="<c:url value="/resources/js/app/services/jquery-fn-wrapper-service.js" />"></script>

<script type="text/javascript">
    var showcaseApp = angular.module('showcaseApp', ['jQueryFnWrapperService', 'businessSegmentFactory']);

    showcaseApp.controller('accountBrowserCtrl', ['$scope', 'modalToggler', 'businessSegmentFactory',
        function($scope, modalToggler, businessSegmentFactory) {

        $scope.segments = [];
        businessSegmentFactory.getSegments().success(function (data) {

            if (data.length > 0) {
                angular.forEach(data, function(segment, key) {
                    segment['selected'] = true;
                    $scope.segments.push(segment);
                });
            }
        });

        $scope.accountBrowserWithSegmentUrl = "/common/account-browser-with-segment";
        $scope.showAccountBrowserWithSegment = function() {
            modalToggler.show('myModal2');
        }
    }]);
</script>