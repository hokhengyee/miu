(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseAccessDeleteController',CourseAccessDeleteController);

    CourseAccessDeleteController.$inject = ['$uibModalInstance', 'entity', 'CourseAccess'];

    function CourseAccessDeleteController($uibModalInstance, entity, CourseAccess) {
        var vm = this;

        vm.courseAccess = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CourseAccess.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
