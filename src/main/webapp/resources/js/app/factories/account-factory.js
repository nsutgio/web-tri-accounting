var accountFactory = angular.module('accountFactory', []);

accountFactory.factory('accountFactory', ['$http', function($http) {
    this.getAccounts = function () {
        return $http.get('/account/list/');
    };
    return this;
}]);