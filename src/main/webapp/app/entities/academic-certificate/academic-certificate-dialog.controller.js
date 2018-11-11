(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('AcademicCertificateDialogController', AcademicCertificateDialogController);

    AcademicCertificateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'AcademicCertificate'];

    function AcademicCertificateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, AcademicCertificate) {
        var vm = this;

        vm.academicCertificate = entity;
        vm.clear = clear;
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
            if (vm.academicCertificate.id !== null) {
                AcademicCertificate.update(vm.academicCertificate, onSaveSuccess, onSaveError);
            } else {
                AcademicCertificate.save(vm.academicCertificate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:academicCertificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setAcademicCertificate1 = function ($file, academicCertificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        academicCertificate.academicCertificate1 = base64Data;
                        academicCertificate.academicCertificate1ContentType = $file.type;
                    });
                });
            }
        };

        vm.setAcademicCertificate2 = function ($file, academicCertificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        academicCertificate.academicCertificate2 = base64Data;
                        academicCertificate.academicCertificate2ContentType = $file.type;
                    });
                });
            }
        };

        vm.setAcademicCertificate3 = function ($file, academicCertificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        academicCertificate.academicCertificate3 = base64Data;
                        academicCertificate.academicCertificate3ContentType = $file.type;
                    });
                });
            }
        };

    }
})();
