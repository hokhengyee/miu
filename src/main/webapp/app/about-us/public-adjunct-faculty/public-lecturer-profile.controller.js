(function() {
	'use strict';

	angular.module('miuApp').controller('PublicLecturerProfileController',
			PublicLecturerProfileController);

	PublicLecturerProfileController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'DataUtils', 'entity',
			'PublicLecturerProfile', 'User', 'Salutation', '$sce' ];

	function PublicLecturerProfileController($scope, $rootScope, $stateParams,
			previousState, DataUtils, entity, PublicLecturerProfile, User,
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
