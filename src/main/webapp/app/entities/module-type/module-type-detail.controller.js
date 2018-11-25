(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ModuleTypeDetailController', ModuleTypeDetailController);

    ModuleTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ModuleType'];

    function ModuleTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, ModuleType) {
        var vm = this;

        vm.moduleType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:moduleTypeUpdate', function(event, result) {
            vm.moduleType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
