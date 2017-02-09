(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseMaterialDialogController', CourseMaterialDialogController);

    CourseMaterialDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CourseMaterial', 'Course'];

    function CourseMaterialDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, CourseMaterial, Course) {
        var vm = this;

        vm.courseMaterial = entity;
        vm.clear = clear;
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
            if (vm.courseMaterial.id !== null) {
                CourseMaterial.update(vm.courseMaterial, onSaveSuccess, onSaveError);
            } else {
                CourseMaterial.save(vm.courseMaterial, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:courseMaterialUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setContent = function ($file, courseMaterial) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        courseMaterial.content = base64Data;
                        courseMaterial.contentContentType = $file.type;
                    });
                });
            }
        };

    }
})();
