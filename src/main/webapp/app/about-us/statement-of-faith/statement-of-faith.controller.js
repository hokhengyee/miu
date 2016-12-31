(function() {
	'use strict';

	angular.module('miuApp').controller('StatementOfFaithController',
			StatementOfFaithController);

	StatementOfFaithController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'entity', 'SOFMsg', '$sce' ];

	function StatementOfFaithController($scope, $rootScope, $stateParams, entity,
			SOFMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();
