(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentPaymentDialogController', StudentPaymentDialogController);

    StudentPaymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'StudentPayment', 'User', 'Course', 'PaymentType'];

    function StudentPaymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, StudentPayment, User, Course, PaymentType) {
        var vm = this;

        vm.studentPayment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();
        vm.courses = Course.query();
        vm.paymenttypes = PaymentType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentPayment.id !== null) {
                StudentPayment.update(vm.studentPayment, onSaveSuccess, onSaveError);
            } else {
                StudentPayment.save(vm.studentPayment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:studentPaymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.paymentDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
