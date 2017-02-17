(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CustomStudentReportTypeDetailController', CustomStudentReportTypeDetailController);

    CustomStudentReportTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomStudentReportType'];

    function CustomStudentReportTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomStudentReportType) {
        var vm = this;

        vm.customStudentReportType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:customStudentReportTypeUpdate', function(event, result) {
            vm.customStudentReportType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
