(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('accredited-centers-msg', {
			parent : 'about-us',
			url : '/accredited-centers',
			data : {
				authorities : [],
				pageTitle : 'Accredited Centers'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'AccreditedCentersController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'AccreditedCentersMsg',
						function($stateParams, AccreditedCentersMsg) {
							return AccreditedCentersMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();