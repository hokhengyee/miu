(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('MyForumMessageDeleteController',MyForumMessageDeleteController);

    MyForumMessageDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyForumRoomMsg'];

    function MyForumMessageDeleteController($uibModalInstance, entity, MyForumRoomMsg) {
        var vm = this;

        vm.forumRoomMessage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
        	MyForumRoomMsg.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
