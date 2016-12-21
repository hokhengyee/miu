(function() {
	'use strict';

	angular.module('miuApp').controller('PublicCourseController',
			PublicCourseController);

	PublicCourseController.$inject = [ '$scope', '$state', 'Course' ];

	function PublicCourseController($scope, $state, Course) {
		var vm = this;

	}
})();
