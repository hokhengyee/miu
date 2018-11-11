(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('RecordOfCertificateDialogController', RecordOfCertificateDialogController);

    RecordOfCertificateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'RecordOfCertificate'];

    function RecordOfCertificateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, RecordOfCertificate) {
        var vm = this;

        vm.recordOfCertificate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.recordOfCertificate.id !== null) {
                RecordOfCertificate.update(vm.recordOfCertificate, onSaveSuccess, onSaveError);
            } else {
                RecordOfCertificate.save(vm.recordOfCertificate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:recordOfCertificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setCertScanFile = function ($file, recordOfCertificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        recordOfCertificate.certScanFile = base64Data;
                        recordOfCertificate.certScanFileContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.certDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
