(function() {
	'use strict';

	angular.module('miuApp').controller('PresidentMsgController',
			PresidentMsgController);

	PresidentMsgController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'$sce', 'StaticPage', 'PresidentMsg' ];

	function PresidentMsgController($scope, $rootScope, $stateParams, $sce,
			StaticPage, PresidentMsg) {
		var vm = this;

		vm.staticPage = PresidentMsg.get();
		console.log(vm.staticPage['staticPage']);
		var key;
		for (key in vm.staticPage) {
			if (key == 'content') {
				console.log("out: " + vm.staticPage[key]);
			}
		}

		vm.htmlComment = $sce.trustAsHtml(vm.staticPage.content);

	}
})();
