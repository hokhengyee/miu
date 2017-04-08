(function() {
	'use strict';

	angular.module('miuApp').controller('AdminResetStudentResultsController',
			AdminResetStudentResultsController);

	AdminResetStudentResultsController.$inject = [ '$timeout', '$scope',
			'$stateParams', 'DataUtils', 'entity', 'AdminResetStudentResults',
			'AlertService', 'StudentUser' ];

	function AdminResetStudentResultsController($timeout, $scope, $stateParams,
			DataUtils, entity, AdminResetStudentResults, AlertService,
			StudentUser) {
		var vm = this;

		vm.studentResultsDto = entity;
		vm.users = StudentUser.query();
		vm.save = save;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function save() {
			vm.isSaving = true;
			AdminResetStudentResults.save(vm.studentResultsDto, onSaveSuccess,
					onSaveError);
		}

		function onSaveSuccess(result) {
			vm.studentResultsDto = AdminResetStudentResults.get();
			AlertService.success("Student Results reset successfully.");
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}
	}
})();
