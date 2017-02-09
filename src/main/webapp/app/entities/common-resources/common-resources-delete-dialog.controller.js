(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CommonResourcesDeleteController',CommonResourcesDeleteController);

    CommonResourcesDeleteController.$inject = ['$uibModalInstance', 'entity', 'CommonResources'];

    function CommonResourcesDeleteController($uibModalInstance, entity, CommonResources) {
        var vm = this;

        vm.commonResources = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CommonResources.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
