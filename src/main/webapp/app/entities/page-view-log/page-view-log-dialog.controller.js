(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('PageViewLogDialogController', PageViewLogDialogController);

    PageViewLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PageViewLog'];

    function PageViewLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PageViewLog) {
        var vm = this;

        vm.pageViewLog = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pageViewLog.id !== null) {
                PageViewLog.update(vm.pageViewLog, onSaveSuccess, onSaveError);
            } else {
                PageViewLog.save(vm.pageViewLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:pageViewLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
