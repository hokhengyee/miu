(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StaticPageTypeDialogController', StaticPageTypeDialogController);

    StaticPageTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StaticPageType'];

    function StaticPageTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StaticPageType) {
        var vm = this;

        vm.staticPageType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.staticPageType.id !== null) {
                StaticPageType.update(vm.staticPageType, onSaveSuccess, onSaveError);
            } else {
                StaticPageType.save(vm.staticPageType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:staticPageTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
