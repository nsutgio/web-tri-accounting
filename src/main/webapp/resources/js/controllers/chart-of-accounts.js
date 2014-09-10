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


coaControllers.controller('newAccountController', ['$scope', '$http', '$location', function($scope, $http, $location) {

    $scope.shop = {};
    $scope.errors = {};
    $scope.submitting = false;
    $scope.panelTitle = 'Create new shop';
    var resourceURI = baseURL + '/create';

    alert(resourceURI);
//    if(!($routeParams.shopId === undefined)) {  // update mode
//        $scope.panelTitle = 'Update shop';
//
//        $scope.shopId = $routeParams.shopId;
//        $http.get(baseURL + 'shop/'+ $scope.shopId + '/edit').success(function(data) {
//            if (data === '') {    // not found
//                window.location.href = '#/shop';
//            } else {
//                $scope.shop = data;
//            }
//        });
//        resourceURI = baseURL + 'shop/edit';
//    }

    $scope.processForm = function() {
        $scope.errors = {};
        $scope.submitting = true;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = $('input[name=_csrf]').val();
        $http({
            method: 'POST',
            url: resourceURI,
            data: $scope.shop
        }).success(function(data) {
            if (!data.success) {
                // if not successful, bind errors to error variables
                var messages = data.messages;
                for (var idx = 0; idx < messages.length; idx++) {
                    var field = 'err' + data.fields[idx];
                    var message = data.messages[idx];
                    $scope.errors[field] = message;
                }
            } else {
                // if successful, bind success message to message
                $scope.message = data.successMessage;

                setTimeout(function () {
                    $scope.$apply(function () {
                        window.location.href = '#/shop';
                    });
                }, 4000);
            }
        });
    };
}]);