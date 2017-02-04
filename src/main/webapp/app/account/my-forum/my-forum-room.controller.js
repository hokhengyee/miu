(function() {
	'use strict';

	angular.module('miuApp').controller('MyForumRoomController',
			MyForumRoomController);

	MyForumRoomController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'previousState', 'entity', 'MyForumRoom' ];

	function MyForumRoomController($scope, $rootScope, $stateParams,
			previousState, entity, MyForumRoom) {
		var vm = this;

		vm.forumRoom = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('miuApp:forumRoomUpdate', function(
				event, result) {
			vm.forumRoom = result;
		});
		
		$scope.$on('$destroy', unsubscribe);
		
		vm.messages = [];
	}
})();
