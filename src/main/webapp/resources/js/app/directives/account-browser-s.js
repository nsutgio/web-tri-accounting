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

                var loadAccounts = function () {
                    accountFactory.getAccountsBySegment(collectionSegmentIds()).success(function (data) {
                        console.log(data);
                        scope.accounts = data;
                    });
                }

                var collectionSegmentIds = function () {
                    var segmentIds = [];
                    angular.forEach(scope.segments, function (segment, key) {
                        if (segment.selected) {
                            segmentIds.push(segment.id);
                        }
                    });
                    return segmentIds;
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
            }
        };
    }]);
}).call(this);
