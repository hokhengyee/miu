(function() {
	'use strict';

	angular.module('miuApp').controller('MyProfileController',
			MyProfileController);

	MyProfileController.$inject = [ '$stateParams', 'MyProfileUser',
			'MyStudentProfile', 'Auth', 'Principal' ];

	function MyProfileController($stateParams, MyProfileUser, MyStudentProfile,
			Auth, Principal) {
		var vm = this;

		vm.load = load;
		vm.user = {};

		Principal.identity().then(function(account) {
			vm.account = account;
			vm.load(account.login);
		});

		function load(login) {
			MyProfileUser.get({
				login : login
			}, function(result) {
				vm.user = result;
			});

			MyStudentProfile.get({}, function(result) {
				vm.studentProfile = result;
			});
		}
	}
})();
