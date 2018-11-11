(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ModuleTypeDeleteController',ModuleTypeDeleteController);

    ModuleTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ModuleType'];

    function ModuleTypeDeleteController($uibModalInstance, entity, ModuleType) {
        var vm = this;

        vm.moduleType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ModuleType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
