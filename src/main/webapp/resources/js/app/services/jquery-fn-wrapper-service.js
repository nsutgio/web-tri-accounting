var errorHandlerService = angular.module('jQueryFnWrapperService', []);

errorHandlerService.service('modalToggler', function() {
    this.show = function(modalId) {
        modalId = '#' + modalId
        $(modalId).modal('show');
    };

    this.hide = function(modalId) {
        modalId = '#' + modalId
        $(modalId).modal('hide');
    };
});