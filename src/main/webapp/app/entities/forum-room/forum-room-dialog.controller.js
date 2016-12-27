(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ForumRoomDialogController', ForumRoomDialogController);

    ForumRoomDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ForumRoom'];

    function ForumRoomDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ForumRoom) {
        var vm = this;

        vm.forumRoom = entity;
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
            if (vm.forumRoom.id !== null) {
                ForumRoom.update(vm.forumRoom, onSaveSuccess, onSaveError);
            } else {
                ForumRoom.save(vm.forumRoom, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:forumRoomUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
