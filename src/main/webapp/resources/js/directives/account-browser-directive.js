(function () {
    var accountBrowser;
    accountBrowser = angular.module('accountBrowser', []);

    accountBrowser.controller('accountBrowserCtrl',  ['$scope', '$http', function($scope, $http) {
        $scope.accounts = [];
        $scope.selectedAccount = {};
        $scope.header = 'Browse accounts';
        $scope.body = 'Accounts';

        $scope.myRightButton = function (bool) {
            alert('first function call!');
        };
    }]);

    accountBrowser.directive('modal', function () {
        return {
            restrict: 'EA',
            scope: {
                title: '=modalTitle',
                header: '=modalHeader',
                body: '=modalBody',
                callbackbuttonleft: '&ngClickLeftButton',
                callbackbuttonright: '&ngClickRightButton',
                handler: '=lolo'
            },
            templateUrl: '/common/account-browser',
            transclude: true,
            controller: function ($scope) {
                $scope.handler = 'pop';
            }
        };
    });
}).call(this);