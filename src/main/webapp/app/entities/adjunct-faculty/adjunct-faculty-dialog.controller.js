(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('AdjunctFacultyDialogController', AdjunctFacultyDialogController);

    AdjunctFacultyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AdjunctFaculty', 'AllLecturerProfile'];

    function AdjunctFacultyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AdjunctFaculty, AllLecturerProfile) {
        var vm = this;

        vm.adjunctFaculty = entity;
        vm.clear = clear;
        vm.save = save;
        vm.lecturerprofiles = AllLecturerProfile.get();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.adjunctFaculty.id !== null) {
                AdjunctFaculty.update(vm.adjunctFaculty, onSaveSuccess, onSaveError);
            } else {
                AdjunctFaculty.save(vm.adjunctFaculty, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:adjunctFacultyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
