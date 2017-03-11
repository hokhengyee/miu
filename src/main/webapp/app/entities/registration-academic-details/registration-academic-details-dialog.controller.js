(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('RegistrationAcademicDetailsDialogController', RegistrationAcademicDetailsDialogController);

    RegistrationAcademicDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RegistrationAcademicDetails'];

    function RegistrationAcademicDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RegistrationAcademicDetails) {
        var vm = this;

        vm.registrationAcademicDetails = entity;
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
            if (vm.registrationAcademicDetails.id !== null) {
                RegistrationAcademicDetails.update(vm.registrationAcademicDetails, onSaveSuccess, onSaveError);
            } else {
                RegistrationAcademicDetails.save(vm.registrationAcademicDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:registrationAcademicDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
