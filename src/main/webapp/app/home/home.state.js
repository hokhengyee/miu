(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('home', {
			parent : 'app',
			url : '/',
			data : {
				authorities : []
			},
			views : {
				'content@' : {
					templateUrl : 'app/home/home.html',
					controller : 'HomeController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'HomeMsg',
						function($stateParams, HomeMsg) {
							return HomeMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
