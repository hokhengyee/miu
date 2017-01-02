(function() {
	'use strict';

	angular.module('miuApp').controller('RefundPolicyController',
			RefundPolicyController);

	RefundPolicyController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'entity', 'RefundPolicyMsg', '$sce' ];

	function RefundPolicyController($scope, $rootScope, $stateParams, entity,
			RefundPolicyMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();
