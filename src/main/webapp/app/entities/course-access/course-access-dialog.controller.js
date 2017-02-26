(function() {
	'use strict';

	angular.module('miuApp').controller('CourseAccessDialogController',
			CourseAccessDialogController);

	CourseAccessDialogController.$inject = [ '$timeout', '$scope',
			'$stateParams', '$uibModalInstance', 'entity', 'CourseAccess',
			'AllUser', 'Course' ];

	function CourseAccessDialogController($timeout, $scope, $stateParams,
			$uibModalInstance, entity, CourseAccess, AllUser, Course) {
		var vm = this;

		vm.courseAccess = entity;
		vm.clear = clear;
		vm.save = save;
		vm.users = AllUser.query();
		vm.courses = Course.query();

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if (vm.courseAccess.id !== null) {
				CourseAccess
						.update(vm.courseAccess, onSaveSuccess, onSaveError);
			} else {
				CourseAccess.save(vm.courseAccess, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('miuApp:courseAccessUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

	}
})();
