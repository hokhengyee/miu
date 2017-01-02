(function() {
	'use strict';

	angular.module('miuApp').controller('GradingController', GradingController);

	GradingController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'entity', 'GradingMsg', '$sce' ];

	function GradingController($scope, $rootScope, $stateParams, entity,
			GradingMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

	}
})();