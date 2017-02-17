(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CustomStudentReportTypeDeleteController',CustomStudentReportTypeDeleteController);

    CustomStudentReportTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomStudentReportType'];

    function CustomStudentReportTypeDeleteController($uibModalInstance, entity, CustomStudentReportType) {
        var vm = this;

        vm.customStudentReportType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomStudentReportType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
