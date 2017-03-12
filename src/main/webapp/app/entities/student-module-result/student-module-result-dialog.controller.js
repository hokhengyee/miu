(function() {
	'use strict';

	angular.module('miuApp').controller('StudentModuleResultDialogController',
			StudentModuleResultDialogController);

	StudentModuleResultDialogController.$inject = [ '$timeout', '$scope',
			'$stateParams', '$uibModalInstance', 'entity',
			'StudentModuleResult', 'StudentUser', 'Module' ];

	function StudentModuleResultDialogController($timeout, $scope,
			$stateParams, $uibModalInstance, entity, StudentModuleResult,
			StudentUser, Module) {
		var vm = this;

		vm.studentModuleResult = entity;
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.save = save;
		vm.users = StudentUser.query();
		vm.modules = Module.query();

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if (vm.studentModuleResult.id !== null) {
				StudentModuleResult.update(vm.studentModuleResult,
						onSaveSuccess, onSaveError);
			} else {
				StudentModuleResult.save(vm.studentModuleResult, onSaveSuccess,
						onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('miuApp:studentModuleResultUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.datePickerOpenStatus.dateGraded = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}
	}
})();
