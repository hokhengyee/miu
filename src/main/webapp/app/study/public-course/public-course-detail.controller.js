(function() {
	'use strict';

	angular.module('miuApp').controller('PublicCourseDetailController',
			PublicCourseDetailController);

	PublicCourseDetailController.$inject = [ '$scope', '$stateParams',
			'previousState', 'entity', 'PublicCourse' ];

	function PublicCourseDetailController($scope, $stateParams, previousState,
			entity, PublicCourse) {
		var vm = this;

		vm.course = entity;
		vm.previousState = previousState.name;

		// var unsubscribe = $rootScope.$on('miuApp:courseUpdate',
		// function(event,
		// result) {
		// vm.course = result;
		// });
//		$scope.$on('$destroy', unsubscribe);
	}
})();
