(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ForumRoomDeleteController',ForumRoomDeleteController);

    ForumRoomDeleteController.$inject = ['$uibModalInstance', 'entity', 'ForumRoom'];

    function ForumRoomDeleteController($uibModalInstance, entity, ForumRoom) {
        var vm = this;

        vm.forumRoom = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ForumRoom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
