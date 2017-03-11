(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('OnlineApplicationDeleteController',OnlineApplicationDeleteController);

    OnlineApplicationDeleteController.$inject = ['$uibModalInstance', 'entity', 'OnlineApplication', 'OAAcademicDetails',
		'OAMinisterialWorkExperience'];

    function OnlineApplicationDeleteController($uibModalInstance, entity, OnlineApplication, OAAcademicDetails,
			OAMinisterialWorkExperience) {
        var vm = this;

        vm.onlineApplication = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
        	OAAcademicDetails.delete({md5key : vm.onlineApplication.md5key},
                    function () {
                        console.log("Deleted Academic Details...");
                    });
        	OAMinisterialWorkExperience.delete({md5key : vm.onlineApplication.md5key},
                    function () {
        				console.log("Deleted Ministerial Work Experience...");
                    });
            OnlineApplication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
