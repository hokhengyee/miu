(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseMaterialDeleteController',CourseMaterialDeleteController);

    CourseMaterialDeleteController.$inject = ['$uibModalInstance', 'entity', 'CourseMaterial'];

    function CourseMaterialDeleteController($uibModalInstance, entity, CourseMaterial) {
        var vm = this;

        vm.courseMaterial = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CourseMaterial.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
