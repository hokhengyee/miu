(function() {
	'use strict';

	angular.module('miuApp').controller('ApplyCourseController',
			ApplyCourseController);

	ApplyCourseController.$inject = [ '$timeout', '$scope', '$stateParams',
			'DataUtils', 'entity', 'entity2', 'entity3',
			'PublicOnlineApplication', 'PublicCourse',
			'PublicRegistrationAcademicDetails',
			'PublicMinisterialWorkExperience', 'md5', '$state' ];

	function ApplyCourseController($timeout, $scope, $stateParams, DataUtils,
			entity, entity2, entity3, PublicOnlineApplication, PublicCourse,
			PublicRegistrationAcademicDetails, PublicMinisterialWorkExperience,
			md5, $state) {
		var vm = this;

		$scope.$watch('vm.onlineApplication.email', function() {
			$scope.md5key = md5.createHash(vm.onlineApplication.email || '');
			vm.onlineApplication.md5key = $scope.md5key;
			vm.registrationAcademicDetails.md5key = $scope.md5key;
			vm.ministerialWorkExperience.md5Key = $scope.md5key;
		})

		vm.onlineApplication = entity;
		vm.registrationAcademicDetails = entity2;
		vm.ministerialWorkExperience = entity3;
		vm.onlineapplications = PublicOnlineApplication.query();

		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;
		vm.save = save;
		vm.courses = PublicCourse.query();
		vm.error = false;

		vm.surname = null;
		vm.givenName = null;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function save() {
			vm.surname = vm.onlineApplication.surname;
			vm.givenName = vm.onlineApplication.givenName;
			PublicOnlineApplication.save(vm.onlineApplication, onSaveSuccess1,
					onSaveError1);
		}

		function saveAcademicDetails() {
			PublicRegistrationAcademicDetails.save(
					vm.registrationAcademicDetails, onSaveSuccess2,
					onSaveError2);
		}

		function saveMWE() {
			PublicMinisterialWorkExperience.save(vm.ministerialWorkExperience,
					onSaveSuccess3, onSaveError3);
		}

		function onSaveSuccess1(result) {
			console.log("Saved Online Application...");
			saveAcademicDetails();
		}

		function onSaveError1() {
			// vm.isSaving = false;
			vm.error = true;
		}

		function onSaveSuccess2(result) {
			console.log("Saved Academic Details ...");
			saveMWE();
		}

		function onSaveError2() {
			// vm.isSaving = false;
			vm.error = true;
		}

		function onSaveSuccess3(result) {
			console.log("Saved Ministerial Work Experience ...");
			$state.go('public-online-application-success');
		}

		function onSaveError3() {
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
