(function() {
	'use strict';

	angular.module('miuApp').controller('FacultyAlumniController',
			FacultyAlumniController);

	FacultyAlumniController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'entity', 'FacultyAlumniMsg', '$sce' ];

	function FacultyAlumniController($scope, $rootScope, $stateParams, entity,
			FacultyAlumniMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();
