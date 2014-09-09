// get application's base URL
var baseURL = document.location.origin + document.location.pathname;
var shopListApp = angular.module('coaApp', [
    'ngRoute',
    'coaControllers'
]);

shopListApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/admin/coa', {
                templateUrl: 'admin/coa/accounts-tree-page',
                controller: 'accountTreeController'
            }).
            otherwise({
                redirectTo: '/admin/coa'
            });
    }]);