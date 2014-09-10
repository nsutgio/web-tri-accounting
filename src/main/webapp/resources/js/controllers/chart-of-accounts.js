var coaControllers = angular.module('coaControllers', []);

coaControllers.controller('accountTreeController', ['$scope', '$http', '$location', function($scope, $http, $location) {
    $scope.accounts = {};

    $http.get(baseURL + '/accounts').success(function(data) {
        if (data.length > 0) {
            $scope.accounts = data;
        }
    }).error(function(data) {
        alert("Something went wrong!");
    });
}]);