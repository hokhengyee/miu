(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentOtherResultDeleteController',StudentOtherResultDeleteController);

    StudentOtherResultDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentOtherResult'];

    function StudentOtherResultDeleteController($uibModalInstance, entity, StudentOtherResult) {
        var vm = this;

        vm.studentOtherResult = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentOtherResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
