(function() {
	'use strict';

	angular.module('miuApp').controller('PublicCourseDetailController',
			PublicCourseDetailController);

	PublicCourseDetailController.$inject = [ '$scope', '$stateParams',
			'previousState', 'entity', 'PublicCourse',
			'PublicModuleTheological', 'PublicModulePracticalMinistry' ];

	function PublicCourseDetailController($scope, $stateParams, previousState,
			entity, PublicCourse, PublicModuleTheological,
			PublicModulePracticalMinistry) {
		var vm = this;

		vm.course = entity;
		vm.previousState = previousState.name;
		vm.theological = PublicModuleTheological.get(entity);
		vm.practicalMinistry = PublicModulePracticalMinistry.get(entity);

	}
})();
