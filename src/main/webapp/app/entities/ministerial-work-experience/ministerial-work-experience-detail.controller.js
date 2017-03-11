(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('MinisterialWorkExperienceDetailController', MinisterialWorkExperienceDetailController);

    MinisterialWorkExperienceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MinisterialWorkExperience'];

    function MinisterialWorkExperienceDetailController($scope, $rootScope, $stateParams, previousState, entity, MinisterialWorkExperience) {
        var vm = this;

        vm.ministerialWorkExperience = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:ministerialWorkExperienceUpdate', function(event, result) {
            vm.ministerialWorkExperience = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
