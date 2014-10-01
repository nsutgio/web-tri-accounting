var accountFactory = angular.module('accountFactory', []);

accountFactory.factory('accountFactory', ['$http', function($http) {
    this.getAccounts = function () {
        return $http.get('/account/list/');
    };

    this.getAccountsExcept = function (accountId) {
        return $http.get('/account/' + accountId + '/except');
    };

    this.getAccount = function (accountId) {
        return $http.get('/account/'+accountId);
    };

    this.getAccountTypes = function () {
        return $http.get('/account/type/list');
    };

    this.getAccountGroups = function () {
        return $http.get('/account/group/list');
    };
    return this;
}]);