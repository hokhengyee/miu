(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseMaterialAccessDeleteController',CourseMaterialAccessDeleteController);

    CourseMaterialAccessDeleteController.$inject = ['$uibModalInstance', 'entity', 'CourseMaterialAccess'];

    function CourseMaterialAccessDeleteController($uibModalInstance, entity, CourseMaterialAccess) {
        var vm = this;

        vm.courseMaterialAccess = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CourseMaterialAccess.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
