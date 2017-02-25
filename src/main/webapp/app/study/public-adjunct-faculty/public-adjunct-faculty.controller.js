(function() {
	'use strict';

	angular.module('miuApp').controller('PublicAdjunctFacultyController',
			PublicAdjunctFacultyController);

	PublicAdjunctFacultyController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'entity', 'PublicAdjunctFacultyMsg', '$sce' ];

	function PublicAdjunctFacultyController($scope, $rootScope, $stateParams,
			entity, PublicAdjunctFacultyMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();
