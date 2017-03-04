(function() {
	'use strict';

	angular.module('miuApp').controller('ApplyCourseController',
			ApplyCourseController);

	ApplyCourseController.$inject = [ '$timeout', '$scope', '$stateParams',
			'DataUtils', 'entity', 'PublicOnlineApplication', 'PublicCourse',
			'$state' ];

	function ApplyCourseController($timeout, $scope, $stateParams, DataUtils,
			entity, PublicOnlineApplication, PublicCourse, $state) {
		var vm = this;

		vm.onlineApplication = entity;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.save = save;
		vm.courses = PublicCourse.query();
		vm.error = false;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function save() {
			PublicOnlineApplication.save(vm.onlineApplication, onSaveSuccess,
					onSaveError);
		}

		function onSaveSuccess(result) {
			$state.go('public-online-application-success');
		}

		function onSaveError() {
			// vm.isSaving = false;
			vm.error = true;
		}

		vm.datePickerOpenStatus.dateOfBirth = false;
		vm.datePickerOpenStatus.registrationDatetime = false;

		vm.setApplicationForm = function($file, onlineApplication) {
			if ($file) {
				DataUtils
						.toBase64(
								$file,
								function(base64Data) {
									$scope
											.$apply(function() {
												onlineApplication.applicationForm = base64Data;
												onlineApplication.applicationFormContentType = $file.type;
											});
								});
			}
		};

		vm.setProfilePhoto = function($file, onlineApplication) {
			if ($file && $file.$error === 'pattern') {
				return;
			}
			if ($file) {
				DataUtils.toBase64($file, function(base64Data) {
					$scope.$apply(function() {
						onlineApplication.profilePhoto = base64Data;
						onlineApplication.profilePhotoContentType = $file.type;
					});
				});
			}
		};

		vm.setAcademicCertificate = function($file, onlineApplication) {
			if ($file) {
				DataUtils
						.toBase64(
								$file,
								function(base64Data) {
									$scope
											.$apply(function() {
												onlineApplication.academicCertificate = base64Data;
												onlineApplication.academicCertificateContentType = $file.type;
											});
								});
			}
		};

		vm.setLetterOfRecommendation = function($file, onlineApplication) {
			if ($file) {
				DataUtils
						.toBase64(
								$file,
								function(base64Data) {
									$scope
											.$apply(function() {
												onlineApplication.letterOfRecommendation = base64Data;
												onlineApplication.letterOfRecommendationContentType = $file.type;
											});
								});
			}
		};

		vm.setProfileDocument = function($file, onlineApplication) {
			if ($file) {
				DataUtils
						.toBase64(
								$file,
								function(base64Data) {
									$scope
											.$apply(function() {
												onlineApplication.profileDocument = base64Data;
												onlineApplication.profileDocumentContentType = $file.type;
											});
								});
			}
		};

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}

	}
})();
