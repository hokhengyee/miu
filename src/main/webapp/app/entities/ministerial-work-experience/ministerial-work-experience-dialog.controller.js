(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('MinisterialWorkExperienceDialogController', MinisterialWorkExperienceDialogController);

    MinisterialWorkExperienceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MinisterialWorkExperience'];

    function MinisterialWorkExperienceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MinisterialWorkExperience) {
        var vm = this;

        vm.ministerialWorkExperience = entity;
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
            if (vm.ministerialWorkExperience.id !== null) {
                MinisterialWorkExperience.update(vm.ministerialWorkExperience, onSaveSuccess, onSaveError);
            } else {
                MinisterialWorkExperience.save(vm.ministerialWorkExperience, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:ministerialWorkExperienceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
