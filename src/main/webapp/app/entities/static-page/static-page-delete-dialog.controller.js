(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StaticPageDeleteController',StaticPageDeleteController);

    StaticPageDeleteController.$inject = ['$uibModalInstance', 'entity', 'StaticPage'];

    function StaticPageDeleteController($uibModalInstance, entity, StaticPage) {
        var vm = this;

        vm.staticPage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StaticPage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
