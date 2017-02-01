(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentPaymentDeleteController',StudentPaymentDeleteController);

    StudentPaymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentPayment'];

    function StudentPaymentDeleteController($uibModalInstance, entity, StudentPayment) {
        var vm = this;

        vm.studentPayment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentPayment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
