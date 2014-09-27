var coaControllers = angular.module('coaCtrls', ['ngResource', 'ngSanitize']);

coaControllers.controller('accountDetailsCtrl', ['$scope', '$routeParams', '$http', '$location', function($scope,  $routeParams, $http, $location) {
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

coaControllers.controller('newAccountCtrl', ['$scope', '$routeParams', '$http', 'errorToElementBinder', 'accountFactory',
    function($scope, $routeParams, $http, errorToElementBinder, accountFactory) {

    $scope.account = {};
    // setup defaults
    var accountGroup = {"id" : "1"};
    var accountType = {"id" : "1"};
    var initParentAccount = {"id" : "1"};
    $scope.segments = [];
    $scope.accountGroup = accountGroup;
    $scope.accountType = accountType;
    $scope.account['normalBalance'] = "1";
    $scope.account['accountGroup'] = accountGroup;
    $scope.account['accountType'] = accountType;
    $scope.account['isActive'] = true;
    $scope.account['hasSL'] = false;
    $scope.account['isHeader'] = false;
    $scope.account['parentAccount'] = initParentAccount;
    $scope.errors = {};
    $scope.submitting = false;
    $scope.save ='Save';
    $scope.title = 'Add an account';

    $scope.modalBodyTemplateUrl = "/common/account-browser";
    var resourceURI = '/account/create';

    $http.get('/bus-seg/list').success(function(data) {
        if (data.length > 0) {
            $scope.segments = data;
        }
    }).error(function(data) {
        alert("Failed to fetch business segments.");
    });

    $http.get('/account/type/list').success(function(data) {
        if (data.length > 0) {
            $scope.accountTypes = data;
        }
    }).error(function(data) {
        alert("Failed to fetch account types.");
    });

    $http.get('/account/group/list').success(function(data) {
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
                $scope.parentAccount = data.parentAccount;
                // segments
                angular.forEach(data.segmentAccounts, function(segmentAccount, key) {
                    $scope.checkAssignedSegment(segmentAccount.businessSegment.id);
                });

            }
        }).error(function(a,b,c){
            alert("Something went wrong.");
            window.location.hash = '#/accounts';
        });
        resourceURI = '/account/update';
    } else {
        accountFactory.getAccounts()
            .success(function (data) {
                if (data.length > 0) {
                    $scope.parentAccounts = data;
                }
            })
            .error(function (error) {
                alert('Failed to load accounts.');
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

    $scope.showAccountBrowser = function () {
        // jquery way
        $('#myModal').modal('show');
    }

    $scope.accountSelectedFromBrowser = function (selectedAccount) {
        if (!angular.isUndefined(selectedAccount)) {
            $scope.parentAccount = selectedAccount;
            // jquery way
            $('#myModal').modal('hide');
        }
    }

    $scope.clearAccountSelectedFromBrowser = function () {
        $scope.parentAccount = initParentAccount;
    }

    var newSelectedSegment = [];
    $scope.toggleSegment = function(idx, segment) {
        newSelectedSegment.push(segment.id);
    };

    $scope.processForm = function() {

        $scope.save ='Saving...';

        $scope.errors = {};
        $scope.submitting = true;
        $http.defaults.headers.post['X-CSRF-TOKEN'] = $('input[name=_csrf]').val();

        // create json to be posted to the server
        var jAccount = angular.copy($scope.account);
        jAccount.isActive = jAccount.isActive ? 1 : 0;
        jAccount.hasSL =jAccount.hasSL ? 1 : 0;
        jAccount.isHeader = jAccount.isHeader ? 1 : 0;

        jAccount.accountGroup = angular.copy($scope.accountGroup);
        jAccount.accountType = angular.copy($scope.accountType);
        jAccount.parentAccount = angular.copy($scope.parentAccount);
        jAccount.parentAccountId = jAccount.parentAccount == null ? 0 : jAccount.parentAccount.id;

        var segmentAccounts = [];
        var scopeSegments = angular.copy($scope.segments);
        angular.forEach(scopeSegments, function(segment, key) {
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
        jAccount.segmentAccounts = segmentAccounts;

        console.log(jAccount);

        var res = $http.post(resourceURI, jAccount);
        res.success(function(data) {
            if (!data.success) {
                $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
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

coaControllers.controller('treeGridCtrl',  ['$scope', '$http', '$sce', function($scope, $http, $sce) {
    var tree, myTreeData;
    var rawTreeData;

    $.ajax({
        url:  '/account/list/tree',
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