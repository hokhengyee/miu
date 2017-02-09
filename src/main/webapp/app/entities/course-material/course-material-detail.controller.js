(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseMaterialDetailController', CourseMaterialDetailController);

    CourseMaterialDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CourseMaterial', 'Course'];

    function CourseMaterialDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CourseMaterial, Course) {
        var vm = this;

        vm.courseMaterial = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:courseMaterialUpdate', function(event, result) {
            vm.courseMaterial = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
