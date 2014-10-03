<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<mytag:master>
    <jsp:attribute name="head">
        <%-- includes here --%>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div ng-app="cmnAccountBrowserWithSegmentApp">
            <button title="browse account"  class="btn btn-primary" data-toggle="modal" data-target="#accountWithSegmentModal">Account with Segment</button>
            <div ng-controller="accountBrowserCtrl">
                <div ng-include="templateURL"></div>
            </div>
        </div>
    </jsp:attribute>
</mytag:master>

<script src="<c:url value="/resources/js/app/factories/business-segment-factory.js" />"></script>
<script src="<c:url value="/resources/js/app/factories/account-factory.js" />"></script>

<script type="text/javascript">
    (function() {

        var cmnAccountBrowserWithSegmentApp = angular.module('cmnAccountBrowserWithSegmentApp', ['businessSegmentFactory', 'accountFactory']);

        cmnAccountBrowserWithSegmentApp.controller('accountBrowserCtrl', ['$scope', 'businessSegmentFactory', 'accountFactory',
            function($scope, businessSegmentFactory, accountFactory) {

                $scope.segments = [];
                $scope.accounts = [];
                $scope.templateURL = "/common/account-browser-with-segment";

                businessSegmentFactory.getSegments().success(function (data) {

                    if (data.length > 0) {
                        angular.forEach(data, function(segment, key) {
                            segment['selected'] = true;
                            $scope.segments.push(segment);
                        });

                        $scope.loadAccounts();
                    }
                });

                $scope.loadAccounts = function () {
                    accountFactory.getAccountsBySegment($scope.collectionSegmentIds()).success(function (data) {
                        $scope.accounts = data;
                    });
                }

                $scope.collectionSegmentIds = function () {
                    var segmentIds = [];
                    angular.forEach($scope.segments, function(segment, key) {
                        if (segment.selected) {
                            segmentIds.push(segment.id);
                        }
                    });
                    return segmentIds;
                }
            }]);
    }).call(this);
</script>