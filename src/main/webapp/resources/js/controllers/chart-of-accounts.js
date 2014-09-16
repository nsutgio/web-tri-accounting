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
        $http.get('/account/'+ $scope.accountId).success(function(data) {
            console.log(data);
            if (data === '' || data.id <= 0) {
                window.location.hash = '#/accounts';
            } else {
                $scope.account = data;
            }
        }).error(function(data){
            alert("Something went wrong!");
            window.location.hash = '#/accounts';
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
    var accountGroup = {"id" : "1"};
    var accountType = {"id" : "1"};
    $scope.segments = [];
    $scope.accountGroup = accountGroup;
    $scope.accountType = accountType;
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
    var resourceURI = '/account/create';


    $http.get('/json/bus-seg').success(function(data) {
        if (data.length > 0) {
            $scope.segments = data;
        }
    }).error(function(data) {
        alert("Failed to fetch business segments.");
    });

    $http.get('/json/account-types').success(function(data) {
        if (data.length > 0) {
            $scope.accountTypes = data;
        }
    }).error(function(data) {
        alert("Failed to fetch account types.");
    });

    $http.get('/json/account-groups').success(function(data) {
        if (data.length > 0) {
            $scope.accountGroups = data;
        }
    }).error(function(data) {
        alert("Failed to fetch account groups.");
    });

    if(!($routeParams.accountId === undefined)) {  // update mode
        $scope.title = 'Update account';

        $scope.accountId = $routeParams.accountId;


        $http.get('/account/' + $scope.accountId + '/except').success(function(data) {
            if (data.length > 0) {
                $scope.parentAccounts = data;
            }
        });


        $http.get('/account/'+ $scope.accountId).success(function(data) {
            console.log(data);
            if (data === '' || data.id <= 0) {    // not found
                window.location.hash = '#/account/' + $scope.accountId;
            } else {
                data.isActive = (data.isActive == 1);
                data.isHeader = (data.isHeader == 1);
                data.hasSL = (data.hasSL == 1);

                $scope.account = data;

                $scope.accountType = data.accountType;
                $scope.accountGroup = data.accountGroup;
                // segments
                angular.forEach(data.segmentAccounts, function(segmentAccount, key) {
                    $scope.checkAssignedSegment(segmentAccount.businessSegment.id);
                });

            }
        }).error(function(data){
            alert("Something went wrong!");
            window.location.hash = '#/accounts';
        });
        resourceURI = '/account/update';
    } else {
        $http.get('/account/list').success(function(data) {
            if (data.length > 0) {
                $scope.parentAccounts = data;
            }
        });
    }
    $scope.checkAssignedSegment = function (businessSegmentId) {
        angular.forEach($scope.segments, function(segment, key) {
            if (segment.id == businessSegmentId) {
                segment.selected = true;
                if ($scope.account.id > 0) {
                    // check if is in newly selected segments
                    var index = newSelectedSegment.indexOf(businessSegmentId);
                    if (index < 0) { // not found
                        segment.assigned = true;
                    }
                }
                $scope.segments[key] = segment;
                return;
            }
        });
    }

    var newSelectedSegment = [];
    $scope.toggleSegment = function(idx, segment) {
        newSelectedSegment.push(segment.id);
        console.log(segment.id + " => " + segment.description + " => " + segment.selected);
    };

    $scope.processForm = function() {

        $scope.save ='Saving...';

        $scope.errors = {};
        $scope.submitting = true;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = $('input[name=_csrf]').val();

        $scope.account.isActive = $scope.account.isActive ? 1 : 0;
        $scope.account.hasSL = $scope.account.hasSL ? 1 : 0;
        $scope.account.isHeader = $scope.account.isHeader ? 1 : 0;

        var segmentAccounts = [];
        angular.forEach($scope.segments, function(segment, key) {
            if (segment.selected) {
                delete segment['selected']; // hibernate will complain, so delete it
                delete segment['assigned'];

                var segmentAccount = {
                    "accountCode" : "",
                    "businessSegment" :segment
                };
                segmentAccounts.push(segmentAccount);
            }
        });
        $scope.account.segmentAccounts = segmentAccounts;
        $scope.account.accountGroup = $scope.accountGroup;
        $scope.account.accountType = $scope.accountType;

        console.log($scope.account);

        var res = $http.post(resourceURI, $scope.account);
        res.success(function(data) {
            if (!data.success) {
                console.log($scope.account.segmentAccounts);

                // retain state
                $scope.account.isActive = $scope.account.isActive == 1;
                $scope.account.hasSL = $scope.account.hasSL  == 1;
                $scope.account.isHeader = $scope.account.isHeader == 1;

                // retain segments
                angular.forEach($scope.account.segmentAccounts, function(segmentAccount, key) {
                    $scope.checkAssignedSegment(segmentAccount.businessSegment.id);
                });

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

coaControllers.controller('treeGridController',  ['$scope', '$http', '$sce', function($scope, $http, $sce) {
    var tree, myTreeData;
    var rawTreeData;

    $.ajax({
        url:  '/account/list',
        type: 'GET',
        async: false
    }).done(function(data) {
        rawTreeData = data;
    }).error(function() {
        alert("Something went wrong!");
    });

    myTreeData = getTree(rawTreeData, 'id', 'parentAccountId');
    $scope.tree_data = myTreeData;
    $scope.accounts_tree = tree = {};
    $scope.expanding_property = "title";
    $scope.data_loaded = true;
    $scope.col_defs = [
        { field: "code"},
        { field: "accountType"},
        { field: "id"}
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
}]);