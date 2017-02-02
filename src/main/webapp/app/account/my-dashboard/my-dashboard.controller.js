(function() {
	'use strict';

	angular.module('miuApp').controller('MyDashboardController',
			MyDashboardController);

	MyDashboardController.$inject = [ '$stateParams', 'MyCourseAccess', 'Auth',
			'Principal', 'HomeNewsAndEvents', 'MyExternalOnlineResource',
			'MyCommonResources', 'DataUtils' ];

	function MyDashboardController($stateParams, MyCourseAccess, Auth,
			Principal, HomeNewsAndEvents, MyExternalOnlineResource,
			MyCommonResources, DataUtils) {
		var vm = this;

		vm.load = load;
		vm.user = {};
		vm.courseAccess = null;
		vm.commonResources = [];
		vm.courseMaterials = [];
		vm.openFile = DataUtils.openFile;

		getNewsAndEvents();
		getOnlineResource();
		getCommonResources();

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

		function getCommonResources() {
			vm.commonResources = MyCommonResources.get();
		}
	}
})();
