(function() {
	'use strict';

	angular.module('miuApp').controller('EntryQualificationDetailController',
			EntryQualificationDetailController);

	EntryQualificationDetailController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'entity', 'EntryQualification',
			'Course', '$sce' ];

	function EntryQualificationDetailController($scope, $rootScope,
			$stateParams, previousState, entity, EntryQualification, Course,
			$sce) {
		var vm = this;

		vm.entryQualification = entity;
		vm.previousState = previousState.name;

		vm.htmlComment = $sce.trustAsHtml(vm.entryQualification.content);

		var unsubscribe = $rootScope.$on('miuApp:entryQualificationUpdate',
				function(event, result) {
					vm.entryQualification = result;
				});
		$scope.$on('$destroy', unsubscribe);
	}
})();
