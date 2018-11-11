(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('PageViewLogDeleteController',PageViewLogDeleteController);

    PageViewLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'PageViewLog'];

    function PageViewLogDeleteController($uibModalInstance, entity, PageViewLog) {
        var vm = this;

        vm.pageViewLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PageViewLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
