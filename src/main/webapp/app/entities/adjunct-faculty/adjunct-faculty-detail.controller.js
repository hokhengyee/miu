(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('AdjunctFacultyDetailController', AdjunctFacultyDetailController);

    AdjunctFacultyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AdjunctFaculty', 'LecturerProfile'];

    function AdjunctFacultyDetailController($scope, $rootScope, $stateParams, previousState, entity, AdjunctFaculty, LecturerProfile) {
        var vm = this;

        vm.adjunctFaculty = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:adjunctFacultyUpdate', function(event, result) {
            vm.adjunctFaculty = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
