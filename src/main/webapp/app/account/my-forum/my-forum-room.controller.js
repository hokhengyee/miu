(function() {
	'use strict';

	angular.module('miuApp').controller('MyForumRoomController',
			MyForumRoomController);

	MyForumRoomController.$inject = [ '$scope', '$rootScope', '$stateParams',
			'previousState', 'entity', 'MyForumRoom', 'MyForumRoomMsg',
			'ParseLinks', 'AlertService', 'paginationConstants',
			'pagingParams', 'entity2', '$timeout', 'ForumRoomMessage', 'Auth',
			'Principal' ];

	function MyForumRoomController($scope, $rootScope, $stateParams,
			previousState, entity, MyForumRoom, MyForumRoomMsg, ParseLinks,
			AlertService, paginationConstants, pagingParams, entity2, $timeout,
			ForumRoomMessage, Auth, Principal) {
		var vm = this;
		vm.account = null;

		Principal.identity().then(function(account) {
			vm.account = account;
		});

		vm.forumRoom = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('miuApp:forumRoomUpdate', function(
				event, result) {
			vm.forumRoom = result;
		});

		$scope.$on('$destroy', unsubscribe);

		vm.loadPage = loadPage;
		vm.predicate = pagingParams.predicate;
		vm.reverse = pagingParams.ascending;
		vm.transition = transition;
		vm.itemsPerPage = paginationConstants.itemsPerPage;

		loadAll();

		function loadAll() {
			MyForumRoomMsg.query({
				id : vm.forumRoom.id,
				page : pagingParams.page - 1,
				size : vm.itemsPerPage,
				sort : sort()
			}, onSuccess, onError);

			function sort() {
				var result = [ vm.predicate + ','
						+ (vm.reverse ? 'asc' : 'desc') ];
				if (vm.predicate !== 'id') {
					result.push('id');
				}
				return result;
			}

			function onSuccess(data, headers) {
				vm.links = ParseLinks.parse(headers('link'));
				vm.totalItems = headers('X-Total-Count');
				vm.queryCount = vm.totalItems;
				vm.forumRoomMessages = data;
				vm.page = pagingParams.page;
			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}

		function loadPage(page) {
			vm.page = page;
			vm.transition();
		}

		function transition() {
			$state.transitionTo($state.$current, {
				page : vm.page,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});
		}

		/* Add new message */
		vm.forumRoomMessage = entity2;
		// vm.forumRoomMessage.id = vm.forumRoom.id;
		// vm.clear = clear;
		// vm.datePickerOpenStatus = {};
		// vm.openCalendar = openCalendar;
		vm.save = save;
		// vm.forumrooms = ForumRoom.query();
		// vm.users = User.query();

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		// function clear() {
		// $uibModalInstance.dismiss('cancel');
		// }

		function save() {
			vm.isSaving = true;
			MyForumRoomMsg.save({
				courseID : vm.forumRoom.id,
				message : vm.forumRoomMessage.message
			}, onSaveSuccess, onSaveError);
		}

		function onSaveSuccess(result) {
			$scope.$emit('miuApp:forumRoomMessageUpdate', result);
			// $uibModalInstance.close(result);
			vm.isSaving = false;
			vm.forumRoomMessage = entity2;
			loadAll();
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		// vm.datePickerOpenStatus.messageDatetime = false;

		// function openCalendar(date) {
		// vm.datePickerOpenStatus[date] = true;
		// }

	}
})();
