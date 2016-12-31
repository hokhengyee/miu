(function() {
	'use strict';

	angular.module('miuApp').controller('PresidentMsgController',
			PresidentMsgController);

	PresidentMsgController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'entity', 'PresidentMsg', '$sce' ];

	function PresidentMsgController($scope, $rootScope, $stateParams, entity,
			PresidentMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();
