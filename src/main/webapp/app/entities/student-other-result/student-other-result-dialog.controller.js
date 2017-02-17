(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentOtherResultDialogController', StudentOtherResultDialogController);

    StudentOtherResultDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentOtherResult', 'CustomStudentReportType', 'User'];

    function StudentOtherResultDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentOtherResult, CustomStudentReportType, User) {
        var vm = this;

        vm.studentOtherResult = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customstudentreporttypes = CustomStudentReportType.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentOtherResult.id !== null) {
                StudentOtherResult.update(vm.studentOtherResult, onSaveSuccess, onSaveError);
            } else {
                StudentOtherResult.save(vm.studentOtherResult, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:studentOtherResultUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateGraded = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
