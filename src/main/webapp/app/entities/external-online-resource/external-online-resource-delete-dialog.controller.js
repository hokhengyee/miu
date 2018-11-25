(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ExternalOnlineResourceDeleteController',ExternalOnlineResourceDeleteController);

    ExternalOnlineResourceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExternalOnlineResource'];

    function ExternalOnlineResourceDeleteController($uibModalInstance, entity, ExternalOnlineResource) {
        var vm = this;

        vm.externalOnlineResource = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExternalOnlineResource.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
