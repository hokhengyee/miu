(function() {
	'use strict';

	angular.module('miuApp').controller('MyDashboardController',
			MyDashboardController);

	MyDashboardController.$inject = [ '$stateParams', 'MyCourseAccess', 'Auth',
			'Principal', 'HomeNewsAndEvents', 'MyExternalOnlineResource',
			'MyCommonResources', 'DataUtils', 'MyCourseMaterials' ];

	function MyDashboardController($stateParams, MyCourseAccess, Auth,
			Principal, HomeNewsAndEvents, MyExternalOnlineResource,
			MyCommonResources, DataUtils, MyCourseMaterials) {
		var vm = this;
		var courseID = null;

		vm.load = load;
		vm.user = {};
		vm.courseAccess = null;
		vm.course = null;
		vm.commonResources = [];
		vm.courseMaterialAcessList = [];
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

				if (result.length > 0) {
					courseID = result[0].course.id;

					MyCourseMaterials.get({
						id : courseID
					}, function(result) {
						vm.courseMaterialAcessList = result;
					});
				}
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
