(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('LecturerProfileDeleteController',LecturerProfileDeleteController);

    LecturerProfileDeleteController.$inject = ['$uibModalInstance', 'entity', 'LecturerProfile'];

    function LecturerProfileDeleteController($uibModalInstance, entity, LecturerProfile) {
        var vm = this;

        vm.lecturerProfile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LecturerProfile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
