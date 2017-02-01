(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('SalutationDeleteController',SalutationDeleteController);

    SalutationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Salutation'];

    function SalutationDeleteController($uibModalInstance, entity, Salutation) {
        var vm = this;

        vm.salutation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Salutation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
