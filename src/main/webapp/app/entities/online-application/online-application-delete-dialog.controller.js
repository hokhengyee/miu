(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('OnlineApplicationDeleteController',OnlineApplicationDeleteController);

    OnlineApplicationDeleteController.$inject = ['$uibModalInstance', 'entity', 'OnlineApplication', 'OAAcademicDetails',
		'OAMinisterialWorkExperience', 'OAAcademicCertificate'];

    function OnlineApplicationDeleteController($uibModalInstance, entity, OnlineApplication, OAAcademicDetails,
			OAMinisterialWorkExperience, OAAcademicCertificate) {
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
        	OAAcademicCertificate.delete({md5key : vm.onlineApplication.md5key},
                    function () {
        				console.log("Deleted Academic Certificates...");
                    });
            OnlineApplication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
