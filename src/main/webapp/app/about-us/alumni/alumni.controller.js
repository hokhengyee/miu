(function() {
	'use strict';

	angular.module('miuApp').controller('AlumniController', AlumniController);

	AlumniController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'entity', 'AlumniMsg', '$sce' ];

	function AlumniController($scope, $rootScope, $stateParams, entity,
			AlumniMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();
