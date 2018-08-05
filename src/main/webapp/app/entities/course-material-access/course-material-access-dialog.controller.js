(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseMaterialAccessDialogController', CourseMaterialAccessDialogController);

    CourseMaterialAccessDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CourseMaterialAccess', 'Course', 'AllCourseMaterial'];

    function CourseMaterialAccessDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CourseMaterialAccess, Course, AllCourseMaterial) {
        var vm = this;

        vm.courseMaterialAccess = entity;
        vm.clear = clear;
        vm.save = save;
        vm.courses = Course.query();
        vm.coursematerials = AllCourseMaterial.get();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.courseMaterialAccess.id !== null) {
                CourseMaterialAccess.update(vm.courseMaterialAccess, onSaveSuccess, onSaveError);
            } else {
                CourseMaterialAccess.save(vm.courseMaterialAccess, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:courseMaterialAccessUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
