// get application's base URL
var baseURL = document.location.origin + document.location.pathname;

var firefly = {
    bindErrorsToElements : function(data, scopeErrors) {
        var fields = [];
        try {
            var messages = data.messages;
            for (var idx = 0; idx < messages.length; idx++) {
                var field = 'err_' + data.fields[idx];

                var index = fields.indexOf(field);

                if (index >= 0) { // found
                    var msgs = [];
                    msgs = scopeErrors[field];
                    msgs.push(data.messages[idx]);
                    scopeErrors[field] = msgs;

                } else {    // fresh message
                    scopeErrors[field] = [data.messages[idx]];
                    fields.push(field);
                }
            }
        }catch (err) {}
        return scopeErrors;
    }
}

//coaApp.run(['$rootScope', '$http',
//        function($rootScope, $http){
//            $rootScope.allAccounts = firefly.allAccounts($http);
//            alert('c1');
//            console.log($rootScope.allAccounts);
//        }]
//);