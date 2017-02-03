(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('my-forum', {
			parent : 'account',
			url : '/my-forum',
			data : {
				authorities : [ 'ROLE_USER' ],
				pageTitle : 'Forums'
			},
			views : {
				'content@' : {
					templateUrl : 'app/account/my-forum/my-forum.html',
					controller : 'MyForumController',
					controllerAs : 'vm'
				}
			}
		});
	}
})();
