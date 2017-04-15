(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('alumni-msg', {
			parent : 'about-us',
			url : '/alumni',
			data : {
				authorities : [],
				pageTitle : 'Alumni'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'AlumniController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'AlumniMsg',
						function($stateParams, AlumniMsg) {
							return AlumniMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
