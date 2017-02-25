(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('AdjunctFacultyDeleteController',AdjunctFacultyDeleteController);

    AdjunctFacultyDeleteController.$inject = ['$uibModalInstance', 'entity', 'AdjunctFaculty'];

    function AdjunctFacultyDeleteController($uibModalInstance, entity, AdjunctFaculty) {
        var vm = this;

        vm.adjunctFaculty = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AdjunctFaculty.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
