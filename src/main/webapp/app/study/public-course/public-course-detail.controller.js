(function() {
	'use strict';

	angular.module('miuApp').controller('PublicCourseDetailController',
			PublicCourseDetailController);

	PublicCourseDetailController.$inject = [ '$scope', '$stateParams',
			'previousState', 'entity', 'entity2', 'entity3', 'entity4', '$sce' ];

	function PublicCourseDetailController($scope, $stateParams, previousState,
			entity, entity2, entity3, entity4, $sce) {
		var vm = this;

		vm.course = entity;
		vm.qualifications = entity2;
		vm.theological = entity3;
		vm.practicalMinistry = entity4;
		vm.previousState = previousState.name;
		// vm.theological = PublicModuleTheological.get(entity);
		// vm.practicalMinistry = PublicModulePracticalMinistry.get(entity);
		vm.htmlContent = $sce.trustAsHtml(vm.qualifications.content);

	}
})();
