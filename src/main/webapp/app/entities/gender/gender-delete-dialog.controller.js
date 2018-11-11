(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('GenderDeleteController',GenderDeleteController);

    GenderDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gender'];

    function GenderDeleteController($uibModalInstance, entity, Gender) {
        var vm = this;

        vm.gender = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gender.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
