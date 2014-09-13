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
        {"DemographicId":1,"ParentId":null,"Name":"United States of America","Description":"United States of America", "Area":9826675,"Population":318212000,"TimeZone":"UTC -5 to -10"},
        {"DemographicId":2,"ParentId":1,"Name":"California","Description":"The Tech State","Area":423970,"Population":38340000,"TimeZone":"Pacific Time"},
        {"DemographicId":3,"ParentId":2,"Name":"San Francisco","Description":"The happening city","Area":231,"Population":837442,"TimeZone":"PST"},
        {"DemographicId":4,"ParentId":2,"Name":"Los Angeles","Description":"Disco city","Area":503,"Population":3904657,"TimeZone":"PST"},
        {"DemographicId":5,"ParentId":1,"Name":"Illinois","Description":"Not so cool","Area":57914,"Population":12882135,"TimeZone":"Central Time Zone"},
        {"DemographicId":6,"ParentId":5,"Name":"Chicago","Description":"Financial City","Area":234,"Population":2695598,"TimeZone":"CST"},
        {"DemographicId":7,"ParentId":1,"Name":"Texas","Description":"Rances, Oil & Gas","Area":268581,"Population":26448193,"TimeZone":"Mountain"},
        {"DemographicId":8,"ParentId":1,"Name":"New York","Description":"The largest diverse city","Area":141300,"Population":19651127,"TimeZone":"Eastern Time Zone"},
        {"DemographicId":14,"ParentId":8,"Name":"Manhattan","Description":"Time Square is the place","Area":269.403,"Population":0,"TimeZone":"EST"},
        {"DemographicId":15,"ParentId":14,"Name":"Manhattan City","Description":"Manhattan island","Area":33.77,"Population":0,"TimeZone":"EST"},
        {"DemographicId":16,"ParentId":14,"Name":"Time Square","Description":"Time Square for new year","Area":269.40,"Population":0,"TimeZone":"EST"},
        {"DemographicId":17,"ParentId":8,"Name":"Niagra water fall","Description":"Close to Canada","Area":65.7,"Population":0,"TimeZone":"EST"},
        {"DemographicId":18,"ParentId":8,"Name":"Long Island","Description":"Harbour to Atlantic","Area":362.9,"Population":0,"TimeZone":"EST"},
        {"DemographicId":51,"ParentId":1,"Name":"All_Other","Description":"All_Other demographics","Area":0,"Population":0,"TimeZone":0},
        {"DemographicId":201,"ParentId":null,"Name":"India","Description":"Hydrabad tech city", "Area":9826675,"Population":318212000,"TimeZone":"IST"},
        {"DemographicId":301,"ParentId":null,"Name":"Bangladesh","Description":"Country of love", "Area":9826675,"Population":318212000,"TimeZone":"BST"}
    ];


    var myTreeData = getTree(rawTreeData, 'DemographicId', 'ParentId');

    $scope.tree_data = myTreeData;
    $scope.accounts_tree = tree = {};
    $scope.expanding_property = "Name";
    $scope.col_defs = [
        { field: "Description"},
        { field: "Area"},
        { field: "Population"},
        { field: "TimeZone", displayName: "Time Zone"}
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

    $scope.init = function () {
//        expand_all_parents();
    };
});