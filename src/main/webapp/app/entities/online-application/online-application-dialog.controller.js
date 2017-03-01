(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('OnlineApplicationDialogController', OnlineApplicationDialogController);

    OnlineApplicationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'OnlineApplication', 'Course'];

    function OnlineApplicationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, OnlineApplication, Course) {
        var vm = this;

        vm.onlineApplication = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.courses = Course.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.onlineApplication.id !== null) {
                OnlineApplication.update(vm.onlineApplication, onSaveSuccess, onSaveError);
            } else {
                OnlineApplication.save(vm.onlineApplication, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:onlineApplicationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateOfBirth = false;
        vm.datePickerOpenStatus.registrationDatetime = false;

        vm.setApplicationForm = function ($file, onlineApplication) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        onlineApplication.applicationForm = base64Data;
                        onlineApplication.applicationFormContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
