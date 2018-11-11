(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentResearchPaperResultDeleteController',StudentResearchPaperResultDeleteController);

    StudentResearchPaperResultDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentResearchPaperResult'];

    function StudentResearchPaperResultDeleteController($uibModalInstance, entity, StudentResearchPaperResult) {
        var vm = this;

        vm.studentResearchPaperResult = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentResearchPaperResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
