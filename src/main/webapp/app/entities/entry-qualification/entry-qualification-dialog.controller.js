(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('EntryQualificationDialogController', EntryQualificationDialogController);

    EntryQualificationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EntryQualification', 'Course'];

    function EntryQualificationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EntryQualification, Course) {
        var vm = this;

        vm.entryQualification = entity;
        vm.clear = clear;
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
            if (vm.entryQualification.id !== null) {
                EntryQualification.update(vm.entryQualification, onSaveSuccess, onSaveError);
            } else {
                EntryQualification.save(vm.entryQualification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:entryQualificationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
