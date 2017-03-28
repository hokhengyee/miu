(function() {
	'use strict';

	angular.module('miuApp').controller('OnlineApplicationDetailController',
			OnlineApplicationDetailController);

	OnlineApplicationDetailController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'DataUtils', 'entity',
			'OnlineApplication', 'Course', 'OAAcademicDetails',
			'OAMinisterialWorkExperience', 'OAAcademicCertificate' ];

	function OnlineApplicationDetailController($scope, $rootScope,
			$stateParams, previousState, DataUtils, entity, OnlineApplication,
			Course, OAAcademicDetails, OAMinisterialWorkExperience,
			OAAcademicCertificate) {
		var vm = this;

		vm.onlineApplication = entity;
		vm.previousState = previousState.name;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		vm.academicDetails = OAAcademicDetails.get({
			md5key : vm.onlineApplication.md5key
		});

		vm.mwe = OAMinisterialWorkExperience.get({
			md5key : vm.onlineApplication.md5key
		})

		vm.academicCertificate = OAAcademicCertificate.get({
			md5key : vm.onlineApplication.md5key
		})

		var unsubscribe = $rootScope.$on('miuApp:onlineApplicationUpdate',
				function(event, result) {
					vm.onlineApplication = result;
				});
		$scope.$on('$destroy', unsubscribe);
	}
})();
