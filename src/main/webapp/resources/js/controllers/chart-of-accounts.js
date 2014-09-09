var coaControllers = angular.module('coaControllers', []);

coaControllers.controller('accountTreeController', ['$scope', '$http', '$location', function($scope, $http, $location) {
    $scope.accounts = {};

    $http.get(baseURL + '/accounts').success(function(data) {
        $scope.accounts = data;
        console.log($scope.accounts);
    });
}]);