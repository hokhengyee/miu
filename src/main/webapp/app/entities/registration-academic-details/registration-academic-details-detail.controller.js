(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('RegistrationAcademicDetailsDetailController', RegistrationAcademicDetailsDetailController);

    RegistrationAcademicDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RegistrationAcademicDetails'];

    function RegistrationAcademicDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, RegistrationAcademicDetails) {
        var vm = this;

        vm.registrationAcademicDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:registrationAcademicDetailsUpdate', function(event, result) {
            vm.registrationAcademicDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
