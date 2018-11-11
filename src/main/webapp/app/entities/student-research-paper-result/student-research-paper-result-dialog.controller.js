(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentResearchPaperResultDialogController', StudentResearchPaperResultDialogController);

    StudentResearchPaperResultDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentResearchPaperResult', 'ResearchPaper', 'User'];

    function StudentResearchPaperResultDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentResearchPaperResult, ResearchPaper, User) {
        var vm = this;

        vm.studentResearchPaperResult = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.researchpapers = ResearchPaper.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentResearchPaperResult.id !== null) {
                StudentResearchPaperResult.update(vm.studentResearchPaperResult, onSaveSuccess, onSaveError);
            } else {
                StudentResearchPaperResult.save(vm.studentResearchPaperResult, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:studentResearchPaperResultUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateGraded = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
