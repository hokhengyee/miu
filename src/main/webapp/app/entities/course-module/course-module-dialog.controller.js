(function() {
	'use strict';

	angular.module('miuApp').controller('CourseModuleDialogController',
			CourseModuleDialogController);

	CourseModuleDialogController.$inject = [ '$timeout', '$scope',
			'$stateParams', '$uibModalInstance', 'entity', 'CourseModule',
			'Course', 'AllModule' ];

	function CourseModuleDialogController($timeout, $scope, $stateParams,
			$uibModalInstance, entity, CourseModule, Course, AllModule) {
		var vm = this;

		vm.courseModule = entity;
		vm.clear = clear;
		vm.save = save;
		vm.courses = Course.query();
		vm.modules = AllModule.query();

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if (vm.courseModule.id !== null) {
				CourseModule
						.update(vm.courseModule, onSaveSuccess, onSaveError);
			} else {
				CourseModule.save(vm.courseModule, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('miuApp:courseModuleUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

	}
})();
