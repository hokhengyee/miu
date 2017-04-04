(function() {
	'use strict';

	angular.module('miuApp').controller('AdminUploadROCController',
			AdminUploadROCController);

	AdminUploadROCController.$inject = [ '$timeout', '$scope', '$stateParams',
			'DataUtils', 'entity', 'AdminUploadROC', 'AlertService' ];

	function AdminUploadROCController($timeout, $scope, $stateParams,
			DataUtils, entity, AdminUploadROC, AlertService) {
		var vm = this;

		vm.rocDto = entity;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.save = save;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function save() {
			vm.isSaving = true;
			AdminUploadROC.save(vm.rocDto, onSaveSuccess, onSaveError);
		}

		function onSaveSuccess(result) {
			vm.rocDto = AdminUploadROC.get();
			AlertService.success("Template Uploaded successfully.");
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.setRocTemplate = function($file, rocDto) {
			if ($file) {
				DataUtils.toBase64($file, function(base64Data) {
					$scope.$apply(function() {
						rocDto.rocTemplate = base64Data;
						rocDto.rocTemplateContentType = $file.type;
					});
				});
			}
		};

	}
})();
