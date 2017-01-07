(function() {
	'use strict';

	angular.module('miuApp').controller('PublicCourseDetailController',
			PublicCourseDetailController);

	PublicCourseDetailController.$inject = [ '$scope', '$stateParams',
			'previousState', 'entity', 'entity2', 'entity3', 'entity4',
			'entity5', '$sce' ];

	function PublicCourseDetailController($scope, $stateParams, previousState,
			entity, entity2, entity3, entity4, entity5, $sce) {
		var vm = this;

		vm.course = entity;
		vm.qualifications = entity2;
		vm.theological = entity3;
		vm.practicalMinistry = entity4;
		vm.researchPapers = entity5;
		vm.previousState = previousState.name;

		vm.htmlContent = $sce.trustAsHtml(vm.qualifications.content);
	}
})();
