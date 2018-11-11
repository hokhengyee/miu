(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('RecordOfCertificateDeleteController',RecordOfCertificateDeleteController);

    RecordOfCertificateDeleteController.$inject = ['$uibModalInstance', 'entity', 'RecordOfCertificate'];

    function RecordOfCertificateDeleteController($uibModalInstance, entity, RecordOfCertificate) {
        var vm = this;

        vm.recordOfCertificate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RecordOfCertificate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
