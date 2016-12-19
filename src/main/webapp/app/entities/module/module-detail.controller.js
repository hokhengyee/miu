(function() {
	'use strict';

	angular.module('miuApp').controller('ModuleDetailController',
			ModuleDetailController);

	ModuleDetailController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'previousState', 'entity', 'Module', 'Course' ];

	function ModuleDetailController($scope, $rootScope, $stateParams,
			previousState, entity, Module, Course) {
		var vm = this;

		vm.module = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('miuApp:moduleUpdate', function(event,
				result) {
			vm.module = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
