(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('register-user', {
			parent : 'admin',
			url : '/register-user',
			data : {
				authorities : [ 'ROLE_ADMIN' ],
				pageTitle : 'Registration'
			},
			views : {
				'content@' : {
					templateUrl : 'app/admin/register-user/register-user.html',
					controller : 'RegisterUserController',
					controllerAs : 'vm'
				}
			}
		});
	}
})();
