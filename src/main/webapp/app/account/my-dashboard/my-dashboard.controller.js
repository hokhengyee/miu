(function() {
	'use strict';

	angular.module('miuApp').controller('MyDashboardController',
			MyDashboardController);

	MyDashboardController.$inject = [ '$stateParams', 'MyCourseAccess', 'Auth',
			'Principal' ];

	function MyDashboardController($stateParams, MyCourseAccess, Auth,
			Principal) {
		var vm = this;

		vm.load = load;
		vm.user = {};
		vm.courseAccess = null;

		Principal.identity().then(function(account) {
			vm.account = account;
			vm.load(account.login);
		});

		function load(login) {

			MyCourseAccess.get({}, function(result) {
				vm.courseAccess = result;
			});

		}
	}
})();
