var jsonFactory = angular.module('jsonFactory', []);

jsonFactory.factory('accountFactory', ['$http', function($http) {
    this.getAccounts = function () {
        return $http.get('/account/list/');
    };
    return this;
}]);