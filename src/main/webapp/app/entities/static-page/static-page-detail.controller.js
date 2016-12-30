(function() {
	'use strict';

	angular.module('miuApp').controller('StaticPageDetailController',
			StaticPageDetailController);

	StaticPageDetailController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'entity', 'StaticPage',
			'StaticPageType', '$sce' ];

	function StaticPageDetailController($scope, $rootScope, $stateParams,
			previousState, entity, StaticPage, StaticPageType, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.previousState = previousState.name;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

		var unsubscribe = $rootScope.$on('miuApp:staticPageUpdate', function(
				event, result) {
			vm.staticPage = result;
		});

		$scope.$on('$destroy', unsubscribe);
	}
})();
