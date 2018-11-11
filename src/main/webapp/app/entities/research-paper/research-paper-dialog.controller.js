(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ResearchPaperDialogController', ResearchPaperDialogController);

    ResearchPaperDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResearchPaper', 'Course'];

    function ResearchPaperDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResearchPaper, Course) {
        var vm = this;

        vm.researchPaper = entity;
        vm.clear = clear;
        vm.save = save;
        vm.courses = Course.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.researchPaper.id !== null) {
                ResearchPaper.update(vm.researchPaper, onSaveSuccess, onSaveError);
            } else {
                ResearchPaper.save(vm.researchPaper, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:researchPaperUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
