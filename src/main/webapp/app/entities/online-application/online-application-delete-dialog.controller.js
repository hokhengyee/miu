(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('OnlineApplicationDeleteController',OnlineApplicationDeleteController);

    OnlineApplicationDeleteController.$inject = ['$uibModalInstance', 'entity', 'OnlineApplication'];

    function OnlineApplicationDeleteController($uibModalInstance, entity, OnlineApplication) {
        var vm = this;

        vm.onlineApplication = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OnlineApplication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
