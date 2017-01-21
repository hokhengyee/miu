(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('SalutationDialogController', SalutationDialogController);

    SalutationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Salutation'];

    function SalutationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Salutation) {
        var vm = this;

        vm.salutation = entity;
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
            if (vm.salutation.id !== null) {
                Salutation.update(vm.salutation, onSaveSuccess, onSaveError);
            } else {
                Salutation.save(vm.salutation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:salutationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
