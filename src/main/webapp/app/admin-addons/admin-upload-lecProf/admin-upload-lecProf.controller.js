(function() {
	'use strict';

	angular.module('miuApp').controller('AdminUploadLecProfController',
			AdminUploadLecProfController);

	AdminUploadLecProfController.$inject = [ '$timeout', '$scope',
			'$stateParams', 'DataUtils', 'entity', 'AdminUploadLecProf',
			'AlertService', 'LecturerUser' ];

	function AdminUploadLecProfController($timeout, $scope, $stateParams,
			DataUtils, entity, AdminUploadLecProf, AlertService, LecturerUser) {
		var vm = this;

		vm.lecProfDto = entity;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.users = LecturerUser.query();
		vm.save = save;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function save() {
			vm.isSaving = true;
			AdminUploadLecProf.save(vm.lecProfDto, onSaveSuccess, onSaveError);
		}

		function onSaveSuccess(result) {
			vm.lecProfDto = AdminUploadLecProf.get();
			AlertService.success("Template Uploaded successfully.");
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.setLectProfileTemplate = function($file, lecProfDto) {
			if ($file) {
				DataUtils
						.toBase64(
								$file,
								function(base64Data) {
									$scope
											.$apply(function() {
												lecProfDto.lecturerProfileTemplate = base64Data;
												lecProfDto.lecturerProfileTemplateContentType = $file.type;
											});
								});
			}
		};

	}
})();
