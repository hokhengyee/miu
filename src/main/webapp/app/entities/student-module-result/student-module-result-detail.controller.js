(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentModuleResultDetailController', StudentModuleResultDetailController);

    StudentModuleResultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentModuleResult', 'User', 'Module'];

    function StudentModuleResultDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentModuleResult, User, Module) {
        var vm = this;

        vm.studentModuleResult = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:studentModuleResultUpdate', function(event, result) {
            vm.studentModuleResult = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
