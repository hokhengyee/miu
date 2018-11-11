(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseMaterialAccessDetailController', CourseMaterialAccessDetailController);

    CourseMaterialAccessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CourseMaterialAccess', 'Course', 'CourseMaterial'];

    function CourseMaterialAccessDetailController($scope, $rootScope, $stateParams, previousState, entity, CourseMaterialAccess, Course, CourseMaterial) {
        var vm = this;

        vm.courseMaterialAccess = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:courseMaterialAccessUpdate', function(event, result) {
            vm.courseMaterialAccess = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
