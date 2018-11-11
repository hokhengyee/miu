(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('RegistrationAcademicDetailsDeleteController',RegistrationAcademicDetailsDeleteController);

    RegistrationAcademicDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'RegistrationAcademicDetails'];

    function RegistrationAcademicDetailsDeleteController($uibModalInstance, entity, RegistrationAcademicDetails) {
        var vm = this;

        vm.registrationAcademicDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RegistrationAcademicDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
