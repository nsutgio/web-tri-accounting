(function () {
    var app;
    var app = angular.module('cmnAccountBrowserWithSegmentApp', ['businessSegmentFactory', 'accountFactory']);

    app.controller('accountBrowserCtrl', ['$scope', function ($scope) {
    }]);

    app.directive('accountBrowserS', ['$timeout', 'businessSegmentFactory', 'accountFactory', function ($timeout, businessSegmentFactory, accountFactory) {
        return {
            scope : {
                btnLabel : '@',
                handler: '&'
            },
            restrict: 'AE',
            templateUrl: '/common/account-browser-with-segment',
            link: function (scope, elem, attrs) {

                scope.segments = [];
                var segmentIds = [];

                var loadAccounts = function () {
                    accountFactory.getAccountsBySegment(segmentIds).success(function (data) {
                        scope.accounts = data;
                    });
                }

                var collectionSegmentIds = function () {
                    angular.forEach(scope.segments, function (segment, key) {
                        if (segment.selected) {
                            segmentIds.push(segment.id);
                        }
                    });
                }

                elem.bind('click', function () {
                    if (angular.isDefined(scope.accounts) && scope.accounts.length > 0)  return; // cache

                    scope.$apply(function () {
                        businessSegmentFactory.getSegments().success(function (data) {

                            if (data.length > 0) {
                                angular.forEach(data, function (segment, key) {
                                    segment['selected'] = true;
                                    scope.segments.push(segment);
                                });
                                collectionSegmentIds();
                                loadAccounts();
                            }
                        });
                    });
                });

                // expose selected account to the outside world :)
                scope.selectAccount = function(account) {
                    return $timeout(function() {
                        return scope.handler({
                            account: account
                        });
                    });
                }

                scope.getSelectedSegments = function (businessSegmentId) {
                    angular.forEach(scope.segments, function (segment, key) {
                        if (segment.id == businessSegmentId) {
                            segment.selected = true;
                            if ($scope.account.id > 0) {
                                // check if is in newly selected segments
                                var index = newSelectedSegment.indexOf(businessSegmentId);
                                if (index < 0) { // not found
                                    segment.assigned = true;
                                }
                            }
                            $scope.segments[key] = segment;
                            return;
                        }
                    });
                }

                scope.toggleSegment = function(idx, segment) {
                    if (segment.selected) {
                        segmentIds.push(segment.id);
                    } else {
                        var index = segmentIds.indexOf(segment.id);
                        if (index >= 0) { // exists
                            segmentIds.splice(index, 1); 
                        }
                    }
                    loadAccounts();
                };
            }
        };
    }]);
}).call(this);
