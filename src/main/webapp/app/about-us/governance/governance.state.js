(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('governance-msg', {
			parent : 'about-us',
			url : '/governance',
			data : {
				authorities : [],
				pageTitle : 'Governance'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'GovernanceMsgController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'GovernanceMsg',
						function($stateParams, GovernanceMsg) {
							return GovernanceMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
