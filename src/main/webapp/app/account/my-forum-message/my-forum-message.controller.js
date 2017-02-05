(function() {
	'use strict';

	angular.module('miuApp').controller('MyForumMessageController',
			MyForumMessageController);

	MyForumMessageController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'entity', 'MyForumMsg',
			'ForumRoom', 'User' ];

	function MyForumMessageController($scope, $rootScope, $stateParams,
			previousState, entity, MyForumMsg, ForumRoom, User) {
		var vm = this;

		vm.forumRoomMessage = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('miuApp:forumRoomMessageUpdate',
				function(event, result) {
					vm.forumRoomMessage = result;
				});
		$scope.$on('$destroy', unsubscribe);
	}
})();
