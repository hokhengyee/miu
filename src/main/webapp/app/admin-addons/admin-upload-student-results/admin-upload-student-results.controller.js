(function() {
	'use strict';

	angular.module('miuApp').controller('AdminUploadStudentResultsController',
			AdminUploadStudentResultsController);

	AdminUploadStudentResultsController.$inject = [ '$timeout', '$scope',
			'$stateParams', 'DataUtils', 'entity', 'AdminUploadStudentResults',
			'AlertService', 'StudentUser' ];

	function AdminUploadStudentResultsController($timeout, $scope,
			$stateParams, DataUtils, entity, AdminUploadStudentResults,
			AlertService, StudentUser) {
		var vm = this;

		vm.studentResultsDto = entity;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.users = StudentUser.query();
		vm.save = save;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function save() {
			vm.isSaving = true;
			AdminUploadStudentResults.save(vm.studentResultsDto, onSaveSuccess,
					onSaveError);
		}

		function onSaveSuccess(result) {
			vm.studentResultsDto = AdminUploadStudentResults.get();
			AlertService.success("Template Uploaded successfully.");
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.setStudentResultsTemplate = function($file, studentResultsDto) {
			if ($file) {
				DataUtils
						.toBase64(
								$file,
								function(base64Data) {
									$scope
											.$apply(function() {
												studentResultsDto.studentResultTemplate = base64Data;
												studentResultsDto.studentResultTemplateContentType = $file.type;
											});
								});
			}
		};

	}
})();
