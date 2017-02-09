(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CommonResourcesDialogController', CommonResourcesDialogController);

    CommonResourcesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CommonResources'];

    function CommonResourcesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, CommonResources) {
        var vm = this;

        vm.commonResources = entity;
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
            if (vm.commonResources.id !== null) {
                CommonResources.update(vm.commonResources, onSaveSuccess, onSaveError);
            } else {
                CommonResources.save(vm.commonResources, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:commonResourcesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setContent = function ($file, commonResources) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        commonResources.content = base64Data;
                        commonResources.contentContentType = $file.type;
                    });
                });
            }
        };

    }
})();
