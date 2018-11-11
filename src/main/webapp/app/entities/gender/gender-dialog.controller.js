(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('GenderDialogController', GenderDialogController);

    GenderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gender'];

    function GenderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gender) {
        var vm = this;

        vm.gender = entity;
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
            if (vm.gender.id !== null) {
                Gender.update(vm.gender, onSaveSuccess, onSaveError);
            } else {
                Gender.save(vm.gender, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:genderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
