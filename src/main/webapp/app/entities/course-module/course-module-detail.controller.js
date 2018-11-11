(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseModuleDetailController', CourseModuleDetailController);

    CourseModuleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CourseModule', 'Course', 'Module'];

    function CourseModuleDetailController($scope, $rootScope, $stateParams, previousState, entity, CourseModule, Course, Module) {
        var vm = this;

        vm.courseModule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:courseModuleUpdate', function(event, result) {
            vm.courseModule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
