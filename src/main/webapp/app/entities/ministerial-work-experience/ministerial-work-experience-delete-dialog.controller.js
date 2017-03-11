(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('MinisterialWorkExperienceDeleteController',MinisterialWorkExperienceDeleteController);

    MinisterialWorkExperienceDeleteController.$inject = ['$uibModalInstance', 'entity', 'MinisterialWorkExperience'];

    function MinisterialWorkExperienceDeleteController($uibModalInstance, entity, MinisterialWorkExperience) {
        var vm = this;

        vm.ministerialWorkExperience = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MinisterialWorkExperience.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
