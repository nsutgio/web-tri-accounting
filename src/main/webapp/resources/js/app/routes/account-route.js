
var coaApp = angular.module('account', [
    'ngRoute',
    'accountCtrl',
    'treeGrid',
    'errorHandlerService',
    'jQueryFnWrapperService',
    'accountService',
    'accountFactory',
    'businessSegmentFactory'
]);

coaApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/accounts', {
                templateUrl: 'coa/accounts-tree-page',
                controller: 'treeGridCtrl'
            }).
            when('/new', {
                templateUrl: 'coa/new-account-page'
            }).
            when('/account/:accountId', {
                templateUrl: 'coa/account-details-page',
                controller: 'accountDetailsCtrl'
            }).
            when('/account/:accountId/edit', {
                templateUrl: 'coa/new-account-page'
            }).
            otherwise({
                redirectTo:  '/accounts'
            });
    }]);