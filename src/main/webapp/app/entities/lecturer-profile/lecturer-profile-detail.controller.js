(function() {
	'use strict';

	angular.module('miuApp').controller('LecturerProfileDetailController',
			LecturerProfileDetailController);

	LecturerProfileDetailController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'DataUtils', 'entity',
			'LecturerProfile', 'User', 'Salutation', '$sce' ];

	function LecturerProfileDetailController($scope, $rootScope, $stateParams,
			previousState, DataUtils, entity, LecturerProfile, User,
			Salutation, $sce) {
		var vm = this;

		vm.lecturerProfile = entity;
		vm.previousState = previousState.name;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		vm.academicHistory = $sce
				.trustAsHtml(vm.lecturerProfile.academicHistory);
		vm.professionalHistory = $sce
				.trustAsHtml(vm.lecturerProfile.professionalHistory);
		vm.pastAndCurrentMinistry = $sce
				.trustAsHtml(vm.lecturerProfile.pastAndCurrentMinistry);
		vm.publications = $sce.trustAsHtml(vm.lecturerProfile.publications);
		vm.familyDetails = $sce.trustAsHtml(vm.lecturerProfile.familyDetails);
		vm.reference = $sce.trustAsHtml(vm.lecturerProfile.reference);
		
		var unsubscribe = $rootScope.$on('miuApp:lecturerProfileUpdate',
				function(event, result) {
					vm.lecturerProfile = result;
				});
		$scope.$on('$destroy', unsubscribe);
	}
})();

