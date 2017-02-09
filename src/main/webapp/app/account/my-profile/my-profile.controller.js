(function() {
	'use strict';

	angular.module('miuApp').controller('MyProfileController',
			MyProfileController);

	MyProfileController.$inject = [ '$stateParams', 'MyProfileUser',
			'MyLecturerProfile', 'MyStudentProfile', 'MyCourseAccess', 'Auth',
			'Principal', '$sce' ];

	function MyProfileController($stateParams, MyProfileUser,
			MyLecturerProfile, MyStudentProfile, MyCourseAccess, Auth,
			Principal, $sce) {
		var vm = this;

		vm.load = load;
		vm.user = {};
		vm.studentProfile = null;
		vm.lecturerProfile = null;
		vm.courseAccess = null;

		Principal.identity().then(function(account) {
			vm.account = account;
			vm.load(account.login);
		});

		vm.roles = [];

		function load(login) {
			MyProfileUser.get({
				login : login
			}, function(result) {
				vm.user = result;
				vm.roles = result.authorities;
			});

			if (vm.roles.indexOf("ROLE_STUDENT")) {
				MyStudentProfile.get({}, function(result) {
					vm.studentProfile = result;
				}, function(response) {
					console.log("No Student Profile!");
				});
			}

			if (vm.roles.indexOf("ROLE_LECTURER")) {
				MyLecturerProfile
						.get(
								{},
								function(result) {
									vm.lecturerProfile = result;
									vm.academicHistory = $sce
											.trustAsHtml(vm.lecturerProfile.academicHistory);
									vm.professionalHistory = $sce
											.trustAsHtml(vm.lecturerProfile.professionalHistory);
									vm.pastAndCurrentMinistry = $sce
											.trustAsHtml(vm.lecturerProfile.pastAndCurrentMinistry);
									vm.publications = $sce
											.trustAsHtml(vm.lecturerProfile.publications);
									vm.familyDetails = $sce
											.trustAsHtml(vm.lecturerProfile.familyDetails);
									vm.reference = $sce
											.trustAsHtml(vm.lecturerProfile.reference);
								});
			}

			MyCourseAccess.get({}, function(result) {
				vm.courseAccess = result;
			});

		}
	}
})();
