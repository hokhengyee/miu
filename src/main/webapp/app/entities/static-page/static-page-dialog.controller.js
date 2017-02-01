(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StaticPageDialogController', StaticPageDialogController);

    StaticPageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StaticPage', 'StaticPageType'];

    function StaticPageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StaticPage, StaticPageType) {
        var vm = this;

        vm.staticPage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.staticpagetypes = StaticPageType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.staticPage.id !== null) {
                StaticPage.update(vm.staticPage, onSaveSuccess, onSaveError);
            } else {
                StaticPage.save(vm.staticPage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:staticPageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
