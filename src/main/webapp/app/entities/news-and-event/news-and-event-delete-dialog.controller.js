(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('NewsAndEventDeleteController',NewsAndEventDeleteController);

    NewsAndEventDeleteController.$inject = ['$uibModalInstance', 'entity', 'NewsAndEvent'];

    function NewsAndEventDeleteController($uibModalInstance, entity, NewsAndEvent) {
        var vm = this;

        vm.newsAndEvent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NewsAndEvent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
