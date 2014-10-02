var coaControllers = angular.module('accountCtrl', ['ngResource', 'ngSanitize']);

coaControllers.controller('accountDetailsCtrl', ['$scope', '$routeParams', '$http', 'accountFactory',
    function($scope,  $routeParams, $http, accountFactory) {

    $scope.showDetails = false;

    if(!($routeParams.accountId === undefined)) {
        $scope.title = 'Account details';

        $scope.accountId = $routeParams.accountId;

        accountFactory.getAccount($scope.accountId)
            .success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/accounts';
                } else {
                    $scope.account = data;
                    $scope.showDetails = true;
                }
            })
            .error(function (error) {
                toastr.warning('Account not found!');
                window.location.hash = '#/accounts';
            });

    } else {
        toastr.warning('Account not found!');
        window.location.hash = '#/accounts';
    }

    $scope.pointToEditForm = function() {
        window.location.hash = '#/account/' + $scope.accountId + "/edit";
    }
}]);

coaControllers.controller('newAccountCtrl', ['$scope', '$routeParams', '$http', 'errorToElementBinder', 'accountFactory',
    'modalToggler', 'businessSegmentFactory', 'accountService',
    function($scope, $routeParams, $http, errorToElementBinder, accountFactory, modalToggler, businessSegmentFactory,
             accountService) {

    $scope.account = {};
    $scope.showForm = true;
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

    businessSegmentFactory.getSegments().success(function (data) { $scope.segments = data; });
    accountFactory.getAccountTypes().success(function (data) { $scope.accountTypes = data; });
    accountFactory.getAccountGroups().success(function (data) { $scope.accountGroups = data; });

    if(!($routeParams.accountId === undefined)) {  // update mode
        $scope.title = 'Update account';
        $scope.showForm = false;

        $scope.accountId = $routeParams.accountId;

        accountFactory.getAccountsExcept($scope.accountId)
            .success(function (data) {
                $scope.parentAccounts = data;
            })
            .error(function (error) {
                alert('Failed to load accounts.');
            });

        accountFactory.getAccount($scope.accountId)
            .success(function (data) {
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

                    $scope.showForm = true;
                }
            })
            .error(function (error) {
                toastr.warning('Account not found!');
                window.location.hash = '#/accounts';
            });

        resourceURI = '/account/update';
    } else {
        accountFactory.getAccounts()
            .success(function (data) {
                $scope.parentAccounts = data;
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
        modalToggler.show('myModal');
    }


    $scope.modalBodyTemplateUrl2 = "/common/account-browser-with-segment";
    $scope.showAccountBrowserWithSegment = function() {
        modalToggler.show('myModal2');
    }

    $scope.accountSelectedFromBrowser = function (selectedAccount) {
        if (!angular.isUndefined(selectedAccount)) {
            $scope.parentAccount = selectedAccount;
            modalToggler.hide('myModal');
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
        var jAccount = accountService.createAccountJson($scope);

        var res = $http.post(resourceURI, jAccount);
        res.success(function(data) {
            if (!data.success) {
                $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                $scope.save ='Save';
                $scope.submitting = false;
                toastr.warning('Error found.');
            } else {
                window.location.hash = '#/account/' + data.modelId;
                toastr.success('Account successfully saved!');
            }
        });
        res.error(function(data, status, headers, config) {
            toastr.error('Something went wrong!');
            $scope.save ='Save';
            $scope.submitting = false;
        });
    };
}]);

coaControllers.controller('treeGridCtrl',  ['$scope', 'accountFactory', function($scope, accountFactory) {
    var tree, myTreeData;
    var rawTreeData;

    rawTreeData = accountFactory.getTreeAccounts().data;

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