(function () {
    var accountBrowser;
    accountBrowser = angular.module('accountBrowser', []);

    accountBrowser.directive('modal', function () {
        return {
            restrict: 'EA',
            scope: {
                title: '=modalTitle',
                header: '=modalHeader',
                body: '=modalBody',
                footer: '=modalFooter',
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