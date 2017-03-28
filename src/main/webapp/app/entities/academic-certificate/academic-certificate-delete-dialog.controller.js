(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('AcademicCertificateDeleteController',AcademicCertificateDeleteController);

    AcademicCertificateDeleteController.$inject = ['$uibModalInstance', 'entity', 'AcademicCertificate'];

    function AcademicCertificateDeleteController($uibModalInstance, entity, AcademicCertificate) {
        var vm = this;

        vm.academicCertificate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AcademicCertificate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
