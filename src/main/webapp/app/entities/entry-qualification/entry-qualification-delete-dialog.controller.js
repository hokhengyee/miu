(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('EntryQualificationDeleteController',EntryQualificationDeleteController);

    EntryQualificationDeleteController.$inject = ['$uibModalInstance', 'entity', 'EntryQualification'];

    function EntryQualificationDeleteController($uibModalInstance, entity, EntryQualification) {
        var vm = this;

        vm.entryQualification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EntryQualification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
