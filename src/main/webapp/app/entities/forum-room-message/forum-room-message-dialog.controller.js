(function() {
	'use strict';

	angular.module('miuApp').controller('ForumRoomMessageDialogController',
			ForumRoomMessageDialogController);

	ForumRoomMessageDialogController.$inject = [ '$timeout', '$scope',
			'$stateParams', '$uibModalInstance', 'entity', 'ForumRoomMessage',
			'ForumRoom', 'AllUser' ];

	function ForumRoomMessageDialogController($timeout, $scope, $stateParams,
			$uibModalInstance, entity, ForumRoomMessage, ForumRoom, AllUser) {
		var vm = this;

		vm.forumRoomMessage = entity;
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.save = save;
		vm.forumrooms = ForumRoom.query();
		vm.users = AllUser.query();

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if (vm.forumRoomMessage.id !== null) {
				ForumRoomMessage.update(vm.forumRoomMessage, onSaveSuccess,
						onSaveError);
			} else {
				ForumRoomMessage.save(vm.forumRoomMessage, onSaveSuccess,
						onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('miuApp:forumRoomMessageUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.datePickerOpenStatus.messageDatetime = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}
	}
})();
