
var coaApp = angular.module('coaApp', [
    'ngRoute',
    'coaControllers',
    'treeGrid'
]);

coaApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/accounts', {
                templateUrl: 'coa/accounts-tree-page',
                controller: 'treeGridController'
            }).
            when('/new', {
                templateUrl: 'coa/new-account-page',
                controller: 'newAccountController'
            }).
            when('/account/:accountId', {
                templateUrl: 'coa/account-details-page',
                controller: 'accountDetailsController'
            }).
            when('/account/:accountId/edit', {
                templateUrl: 'coa/new-account-page',
                controller: 'newAccountController'
            }).
            otherwise({
                redirectTo:  '/accounts'
            });
    }]);