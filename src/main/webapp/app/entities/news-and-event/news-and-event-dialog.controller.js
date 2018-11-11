(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('NewsAndEventDialogController', NewsAndEventDialogController);

    NewsAndEventDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'NewsAndEvent'];

    function NewsAndEventDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, NewsAndEvent) {
        var vm = this;

        vm.newsAndEvent = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.newsAndEvent.id !== null) {
                NewsAndEvent.update(vm.newsAndEvent, onSaveSuccess, onSaveError);
            } else {
                NewsAndEvent.save(vm.newsAndEvent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:newsAndEventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDT = false;
        vm.datePickerOpenStatus.endDT = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
