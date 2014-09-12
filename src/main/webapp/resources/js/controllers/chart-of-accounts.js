var coaControllers = angular.module('coaControllers', ['ngResource']);

coaControllers.controller('accountTreeController', ['$scope', '$http', '$location', function($scope, $http, $location) {
    $scope.accounts = [{"code":"Loading data..."}];

    $http.get(baseURL + '/accounts').success(function(data) {
        console.log(data);
        if (data.length > 0) {
            $scope.accounts = data;
        }
    }).error(function(data) {
        alert("Something went wrong!");
    });
}]);


coaControllers.controller('accountDetailsController', ['$scope', '$routeParams', '$http', '$location', function($scope,  $routeParams, $http, $location) {
    if(!($routeParams.accountId === undefined)) {
        $scope.title = 'Account details';

        $scope.accountId = $routeParams.accountId;
        $http.get(baseURL + '/account/'+ $scope.accountId).success(function(data) {
            console.log(data);
            if (data === '') {    // not found
                window.location.hash = '#/accounts';
            } else {
                $scope.account = data;
            }
        });
    } else {
        window.location.hash = '#/accounts';
    }

    $scope.pointToEditForm = function() {
        window.location.hash = '#/account/' + $scope.accountId + "/edit";
    }
}]);


coaControllers.controller('newAccountController', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {

    $scope.account = {};
    // setup defaults
    var accountGroup = {"id" : ""};
    var accountType = {"id" : ""};

    $scope.account['normalBalance'] = "1";
    $scope.account['accountGroup'] = accountGroup;
    $scope.account['accountType'] = accountType;
    $scope.account['parentAccountId'] = "0";
    $scope.account['isActive'] = true;
    $scope.account['parentAccount'] = ""; // workaround
    $scope.errors = {};
    $scope.submitting = false;
    $scope.save ='Save';
    $scope.title = 'Add an account';
    var resourceURI = baseURL + '/create';

    if(!($routeParams.accountId === undefined)) {  // update mode
        $scope.title = 'Update account';

        $scope.accountId = $routeParams.accountId;
        $http.get(baseURL + '/account/'+ $scope.accountId).success(function(data) {
            console.log(data);
            if (data === '') {    // not found
                window.location.hash = '#/account/' + $scope.accountId;
            } else {
                data.isActive = (data.isActive == 1);
                data.isHeader = (data.isHeader == 1);
                data.hasSL = (data.hasSL == 1);

                $scope.account = data;
            }
        });

        resourceURI = baseURL + '/update';
    }

    $scope.processForm = function() {

        $scope.save ='Saving...';

        $scope.errors = {};
        $scope.submitting = true;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = $('input[name=_csrf]').val();

        if ($scope.account.hasOwnProperty('isActive')) {
            $scope.account.isActive = 1;
        }

        if ($scope.account.hasOwnProperty('hasSL')) {
            $scope.account.hasSL = 1;
        }

        if ($scope.account.hasOwnProperty('isHeader')) {
            $scope.account.isHeader = 1;
        }

        console.log($scope.account);

        var res = $http.post(resourceURI, $scope.account);
        res.success(function(data) {
            if (!data.success) {
                console.log(data);

                $scope.errors = bindErrorsToElements(data, $scope.errors);
                $scope.save ='Save';
                $scope.submitting = false;
            } else {
                // if successful, bind success message to message
                $scope.message = data.successMessage;

                setTimeout(function () {
                    $scope.$apply(function () {
                        window.location.hash = '#/account/' + data.modelId;
                    });
                }, 4000);
            }
        });
        res.error(function(data, status, headers, config) {
            alert("Something went wrong!");
            $scope.save ='Save';
            $scope.submitting = false;
        });
    };
}]);