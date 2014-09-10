var coaControllers = angular.module('coaControllers', ['ngResource']);

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

coaControllers.controller('newAccountController', ['$scope', '$routeParams', '$http', '$resource', function($scope, $routeParams, $http,  $resource) {

    $scope.account = {};
    // setup defaults
    var accountGroup = {"id" : ""};
    var accountType = {"id" : ""};
    var parentAccount = {"id" : "0"};

    $scope.account['normalBalance'] = "1";
    $scope.account['accountGroup'] = accountGroup;
    $scope.account['accountType'] = accountType;
    $scope.account['parentAccount'] = parentAccount;
    $scope.account['active'] = true;
    $scope.errors = {};
    $scope.submitting = false;
    $scope.title = 'Add an account';
    var resourceURI = baseURL + '/create';

//    if(!($routeParams.accountId === undefined)) {  // update mode
//        $scope.title = 'Update account';
//
//        $scope.accountId = $routeParams.accountId;
//        $http.get(baseURL + '/account/'+ $scope.accountId + '/edit').success(function(data) {
//            if (data === '') {    // not found
//                window.location.href = '#/accounts';
//            } else {
//                $scope.account = data;
//            }
//        });
//        resourceURI = baseURL + '/account/edit';
//    }

    $scope.processForm = function() {
        $scope.errors = {};
        $scope.submitting = true;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = $('input[name=_csrf]').val();


        console.log($scope.account);
        $scope.account = {
            "title" : "test 1",
            "GLAccount" : "GL",
            "SLAccount" : "SL1",
            "auxiliaryAccount" : "AX1",
            "is_header" : "1",
            "isActive" : "1",
            "has_sl" : "1"
        };

        var res = $http.post(resourceURI, $scope.account);
        res.success(function(data) {
            console.log(data);
            if (!data.success) {
                // if not successful, bind errors to error variables
//                var messages = data.messages;
//                for (var idx = 0; idx < messages.length; idx++) {
//                    var field = 'err' + data.fields[idx];
//                    var message = data.messages[idx];
//                    $scope.errors[field] = message;
//                }
            } else {
                // if successful, bind success message to message
                $scope.message = data.successMessage;

                setTimeout(function () {
                    $scope.$apply(function () {
                        window.location.href = '#/accounts';
                    });
                }, 4000);
            }
        });
        res.error(function(data, status, headers, config) {
            alert("Something went wrong!");
            $scope.submitting = false;
        });
    };
}]);