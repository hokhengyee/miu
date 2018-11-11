(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentModuleResultDeleteController',StudentModuleResultDeleteController);

    StudentModuleResultDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentModuleResult'];

    function StudentModuleResultDeleteController($uibModalInstance, entity, StudentModuleResult) {
        var vm = this;

        vm.studentModuleResult = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentModuleResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
