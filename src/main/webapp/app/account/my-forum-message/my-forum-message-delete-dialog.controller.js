(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('MyForumMessageDeleteController',MyForumMessageDeleteController);

    MyForumMessageDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyForumMsg'];

    function MyForumMessageDeleteController($uibModalInstance, entity, MyForumMsg) {
        var vm = this;

        vm.forumRoomMessage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
        	MyForumMsg.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
