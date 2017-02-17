(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CustomStudentReportTypeDialogController', CustomStudentReportTypeDialogController);

    CustomStudentReportTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomStudentReportType'];

    function CustomStudentReportTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomStudentReportType) {
        var vm = this;

        vm.customStudentReportType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customStudentReportType.id !== null) {
                CustomStudentReportType.update(vm.customStudentReportType, onSaveSuccess, onSaveError);
            } else {
                CustomStudentReportType.save(vm.customStudentReportType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:customStudentReportTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
