(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('EntryQualificationDetailController', EntryQualificationDetailController);

    EntryQualificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EntryQualification', 'Course'];

    function EntryQualificationDetailController($scope, $rootScope, $stateParams, previousState, entity, EntryQualification, Course) {
        var vm = this;

        vm.entryQualification = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:entryQualificationUpdate', function(event, result) {
            vm.entryQualification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
