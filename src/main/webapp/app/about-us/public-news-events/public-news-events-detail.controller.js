(function() {
	'use strict';

	angular.module('miuApp').controller('AUNewsAndEventDetailController',
			AUNewsAndEventDetailController);

	AUNewsAndEventDetailController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'DataUtils', 'entity',
			'AUNewsAndEvent', '$sce' ];

	function AUNewsAndEventDetailController($scope, $rootScope, $stateParams,
			previousState, DataUtils, entity, AUNewsAndEvent, $sce) {
		var vm = this;

		vm.newsAndEvent = entity;
		vm.previousState = previousState.name;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.staticHtml = $sce.trustAsHtml(vm.newsAndEvent.eventDetail);

		var unsubscribe = $rootScope.$on('miuApp:newsAndEventUpdate', function(
				event, result) {
			vm.newsAndEvent = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
