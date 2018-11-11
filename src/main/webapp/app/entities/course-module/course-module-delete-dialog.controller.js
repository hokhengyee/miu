(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CourseModuleDeleteController',CourseModuleDeleteController);

    CourseModuleDeleteController.$inject = ['$uibModalInstance', 'entity', 'CourseModule'];

    function CourseModuleDeleteController($uibModalInstance, entity, CourseModule) {
        var vm = this;

        vm.courseModule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CourseModule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
