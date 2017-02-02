(function() {
	'use strict';

	angular.module('miuApp').controller('MyDashboardController',
			MyDashboardController);

	MyDashboardController.$inject = [ '$stateParams', 'MyCourseAccess', 'Auth',
			'Principal', 'HomeNewsAndEvents', 'MyExternalOnlineResource' ];

	function MyDashboardController($stateParams, MyCourseAccess, Auth,
			Principal, HomeNewsAndEvents, MyExternalOnlineResource) {
		var vm = this;

		vm.load = load;
		vm.user = {};
		vm.courseAccess = null;
		vm.commonResources = [];
		vm.courseMaterials = [];

		getNewsAndEvents();
		getOnlineResource();

		Principal.identity().then(function(account) {
			vm.account = account;
			vm.load(account.login);
		});

		function load(login) {
			MyCourseAccess.get({}, function(result) {
				vm.courseAccess = result;
			});
		}

		function getNewsAndEvents() {
			vm.newsAndEvents = HomeNewsAndEvents.get();
		}

		function getOnlineResource() {
			vm.onlineResource = MyExternalOnlineResource.get();
		}
	}
})();
