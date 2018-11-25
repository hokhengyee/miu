(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ResearchPaperDeleteController',ResearchPaperDeleteController);

    ResearchPaperDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResearchPaper'];

    function ResearchPaperDeleteController($uibModalInstance, entity, ResearchPaper) {
        var vm = this;

        vm.researchPaper = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ResearchPaper.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
