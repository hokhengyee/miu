(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentOtherResultDetailController', StudentOtherResultDetailController);

    StudentOtherResultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentOtherResult', 'CustomStudentReportType', 'User'];

    function StudentOtherResultDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentOtherResult, CustomStudentReportType, User) {
        var vm = this;

        vm.studentOtherResult = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:studentOtherResultUpdate', function(event, result) {
            vm.studentOtherResult = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
