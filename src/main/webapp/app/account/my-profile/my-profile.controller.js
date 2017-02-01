(function() {
	'use strict';

	angular.module('miuApp').controller('MyProfileController',
			MyProfileController);

	MyProfileController.$inject = [ '$stateParams', 'MyProfileUser',
			'MyStudentProfile', 'MyCourseAccess', 'Auth', 'Principal' ];

	function MyProfileController($stateParams, MyProfileUser, MyStudentProfile,
			MyCourseAccess, Auth, Principal) {
		var vm = this;

		vm.load = load;
		vm.user = {};
		vm.studentProfile = null;
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
				});
			}

			MyCourseAccess.get({}, function(result) {
				vm.courseAccess = result;
			});

		}
	}
})();
