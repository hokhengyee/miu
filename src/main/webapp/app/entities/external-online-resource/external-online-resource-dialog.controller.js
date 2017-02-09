(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ExternalOnlineResourceDialogController', ExternalOnlineResourceDialogController);

    ExternalOnlineResourceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ExternalOnlineResource'];

    function ExternalOnlineResourceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ExternalOnlineResource) {
        var vm = this;

        vm.externalOnlineResource = entity;
        vm.clear = clear;
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
            if (vm.externalOnlineResource.id !== null) {
                ExternalOnlineResource.update(vm.externalOnlineResource, onSaveSuccess, onSaveError);
            } else {
                ExternalOnlineResource.save(vm.externalOnlineResource, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:externalOnlineResourceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
