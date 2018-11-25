(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentProfileDeleteController',StudentProfileDeleteController);

    StudentProfileDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentProfile'];

    function StudentProfileDeleteController($uibModalInstance, entity, StudentProfile) {
        var vm = this;

        vm.studentProfile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentProfile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
