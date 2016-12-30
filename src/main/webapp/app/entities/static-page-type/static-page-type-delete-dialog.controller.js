(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StaticPageTypeDeleteController',StaticPageTypeDeleteController);

    StaticPageTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'StaticPageType'];

    function StaticPageTypeDeleteController($uibModalInstance, entity, StaticPageType) {
        var vm = this;

        vm.staticPageType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StaticPageType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
