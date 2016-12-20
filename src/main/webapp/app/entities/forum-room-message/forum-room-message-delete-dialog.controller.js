(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ForumRoomMessageDeleteController',ForumRoomMessageDeleteController);

    ForumRoomMessageDeleteController.$inject = ['$uibModalInstance', 'entity', 'ForumRoomMessage'];

    function ForumRoomMessageDeleteController($uibModalInstance, entity, ForumRoomMessage) {
        var vm = this;

        vm.forumRoomMessage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ForumRoomMessage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
