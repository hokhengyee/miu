(function() {
	'use strict';

	angular.module('miuApp').controller('AccreditedCentersController',
			AccreditedCentersController);

	AccreditedCentersController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'entity', 'AccreditedCentersMsg', '$sce' ];

	function AccreditedCentersController($scope, $rootScope, $stateParams,
			entity, AccreditedCentersMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();