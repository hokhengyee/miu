(function() {
	'use strict';

	angular.module('miuApp').controller('GovernanceMsgController',
			GovernanceMsgController);

	GovernanceMsgController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'entity', 'GovernanceMsg', '$sce' ];

	function GovernanceMsgController($scope, $rootScope, $stateParams, entity,
			GovernanceMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();