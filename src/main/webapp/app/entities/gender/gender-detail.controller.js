(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('GenderDetailController', GenderDetailController);

    GenderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gender'];

    function GenderDetailController($scope, $rootScope, $stateParams, previousState, entity, Gender) {
        var vm = this;

        vm.gender = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:genderUpdate', function(event, result) {
            vm.gender = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
