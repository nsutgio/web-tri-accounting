var coaControllers = angular.module('coaControllers', ['ngResource', 'ngSanitize']);

coaControllers.controller('accountTreeController', ['$scope', '$http', '$sce',  function($scope, $http, $sce) {
    $scope.accounts = [{"code":"Loading data..."}];
    $scope.loaded = false;

    $scope.unsanitizedHtml = function(html) {
        return $sce.trustAsHtml(html);
    };


    $http.get(baseURL + '/accounts').success(function(data) {
        if (data.length > 0) {
            $scope.accounts = data;
            $scope.loaded = true;
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
            if (data === '' || data.id <= 0) {
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
    $scope.account['hasSL'] = false;
    $scope.account['isHeader'] = false;
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
            if (data === '' || data.id <= 0) {    // not found
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

        $scope.account.isActive = $scope.account.isActive ? 1 : 0;
        $scope.account.hasSL = $scope.account.hasSL ? 1 : 0;
        $scope.account.isHeader = $scope.account.isHeader ? 1 : 0;

        console.log($scope.account);

        var res = $http.post(resourceURI, $scope.account);
        res.success(function(data) {
            if (!data.success) {
                console.log(data);

                // retain state
                $scope.account.isActive = $scope.account.isActive == 1;
                $scope.account.hasSL = $scope.account.hasSL  == 1;
                $scope.account.isHeader = $scope.account.isHeader == 1;

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

coaControllers.controller('treeGridController', function($scope, $timeout) {
    var tree;

    var rawTreeData = [
        {"DemographicId":1,"ParentId":null,"Title":"United States of America","Code":"United States of America", "Type":9826675,"Id":318212000},
        {"DemographicId":2,"ParentId":1,"Title":"California","Code":"The Tech State","Type":"Members’ Equity and Margins","Id":38340000},
        {"DemographicId":3,"ParentId":2,"Title":"San Francisco","Code":"The happening city","Type":231,"Id":837442},
        {"DemographicId":4,"ParentId":2,"Title":"Los Angeles","Code":"Disco city","Type":503,"Id":3904657},
        {"DemographicId":5,"ParentId":1,"Title":"Illinois","Code":"Not so cool","Type":57914,"Id":12882135},
        {"DemographicId":6,"ParentId":5,"Title":"Chicago","Code":"Financial City","Type":234,"Id":2695598},
        {"DemographicId":7,"ParentId":1,"Title":"Texas","Code":"Rances, Oil & Gas","Type":268581,"Id":26448193},
        {"DemographicId":8,"ParentId":1,"Title":"New York","Code":"The largest diverse city","Type":141300,"Id":19651127},
        {"DemographicId":14,"ParentId":8,"Title":"Manhattan","Code":"Time Square is the place","Type":269.403,"Id":0},
        {"DemographicId":15,"ParentId":14,"Title":"Manhattan City","Code":"Manhattan island","Type":33.77,"Id":0},
        {"DemographicId":16,"ParentId":14,"Title":"Time Square","Code":"Time Square for new year","Type":269.40,"Id":0},
        {"DemographicId":17,"ParentId":8,"Title":"Niagra water fall","Code":"Close to Canada","Type":65.7,"Id":0},
        {"DemographicId":18,"ParentId":8,"Title":"Long Island","Code":"Harbour to Atlantic","Type":362.9,"Id":0},
        {"DemographicId":51,"ParentId":1,"Title":"All_Other","Code":"All_Other demographics","Type":0,"Id":0},
        {"DemographicId":201,"ParentId":null,"Title":"India","Code":"Hydrabad tech city", "Type":9826675,"Id":318212000},
        {"DemographicId":301,"ParentId":null,"Title":"Bangladesh","Code":"Country of love", "Type":9826675,"Id":318212000}
    ];

    var myTreeData = getTree(rawTreeData, 'DemographicId', 'ParentId');

    $scope.tree_data = myTreeData;
    $scope.accounts_tree = tree = {};
    $scope.expanding_property = "Title";
    $scope.col_defs = [
        { field: "Code"},
        { field: "Type"},
        { field: "Id"}
    ];
    $scope.accounts_tree_handler = function(branch){
        console.log('you clicked on', branch)
    }

    function getTree(data, primaryIdName, parentIdName){
        if(!data || data.length==0 || !primaryIdName ||!parentIdName)
            return [];

        var tree = [],
            rootIds = [],
            item = data[0],
            primaryKey = item[primaryIdName],
            treeObjs = {},
            parentId,
            parent,
            len = data.length,
            i = 0;

        while(i<len){
            item = data[i++];
            primaryKey = item[primaryIdName];
            treeObjs[primaryKey] = item;
            parentId = item[parentIdName];

            if(parentId){
                parent = treeObjs[parentId];

                if(parent.children){
                    parent.children.push(item);
                }
                else{
                    parent.children = [item];
                }
            }
            else{
                rootIds.push(primaryKey);
            }
        }

        for (var i = 0; i < rootIds.length; i++) {
            tree.push(treeObjs[rootIds[i]]);
        };

        return tree;
    }
});