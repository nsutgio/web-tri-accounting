// get application's base URL
var baseURL = document.location.origin + document.location.pathname; 

var shopListApp = angular.module('shopListApp', [
	 'ngRoute',
	 'shopControllers'
   ]);
 
shopListApp.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
	    when('/shop', {
	      templateUrl: 'shop/list-page',
	      controller: 'ShopListCtrl'
	    }).
	    when('/shop/:shopId/edit', {
	      templateUrl: 'shop/edit-page',
	      controller: 'ShopCreateCtrl'
	    }).
            when('/shop/create', {
	      templateUrl: 'shop/create' 
	    }).
            when('/shop/admin', {
	      templateUrl: 'shop/admin' 
	    }).
	    otherwise({
	      redirectTo: '/shop'
	    });
}]);