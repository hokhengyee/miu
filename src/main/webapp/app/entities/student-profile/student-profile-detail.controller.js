(function() {
	'use strict';

	angular.module('miuApp').controller('StudentProfileDetailController',
			StudentProfileDetailController);

	StudentProfileDetailController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'DataUtils', 'entity',
			'StudentProfile', 'Salutation', 'Gender', 'User' ];

	function StudentProfileDetailController($scope, $rootScope, $stateParams,
			previousState, DataUtils, entity, StudentProfile, Salutation,
			Gender, User) {
		var vm = this;

		vm.studentProfile = entity;
		vm.previousState = previousState.name;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		var unsubscribe = $rootScope.$on('miuApp:studentProfileUpdate',
				function(event, result) {
					vm.studentProfile = result;
				});
		$scope.$on('$destroy', unsubscribe);
	}
})();
