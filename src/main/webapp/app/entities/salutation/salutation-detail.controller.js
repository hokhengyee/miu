(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('SalutationDetailController', SalutationDetailController);

    SalutationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Salutation'];

    function SalutationDetailController($scope, $rootScope, $stateParams, previousState, entity, Salutation) {
        var vm = this;

        vm.salutation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:salutationUpdate', function(event, result) {
            vm.salutation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
