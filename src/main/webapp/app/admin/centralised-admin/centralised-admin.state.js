(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'centralised-admin',
						{
							parent : 'admin',
							url : '/admin-management',
							data : {
								authorities : [ 'ROLE_ADMIN' ],
								pageTitle : 'Admin Management'
							},
							views : {
								'content@' : {
									templateUrl : 'app/admin/centralised-admin/centralised-admin.html',
									controller : 'CentralisedAdminController',
									controllerAs : 'vm'
								}
							}
						});
	}
})();
