(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseAccessDetailController', CourseAccessDetailController);

    CourseAccessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CourseAccess', 'User', 'Course'];

    function CourseAccessDetailController($scope, $rootScope, $stateParams, previousState, entity, CourseAccess, User, Course) {
        var vm = this;

        vm.courseAccess = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:courseAccessUpdate', function(event, result) {
            vm.courseAccess = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
