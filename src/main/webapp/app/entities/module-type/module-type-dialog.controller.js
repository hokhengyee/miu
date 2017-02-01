(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ModuleTypeDialogController', ModuleTypeDialogController);

    ModuleTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ModuleType'];

    function ModuleTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ModuleType) {
        var vm = this;

        vm.moduleType = entity;
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
            if (vm.moduleType.id !== null) {
                ModuleType.update(vm.moduleType, onSaveSuccess, onSaveError);
            } else {
                ModuleType.save(vm.moduleType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:moduleTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
