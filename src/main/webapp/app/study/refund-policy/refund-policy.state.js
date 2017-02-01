(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('refund-policy-msg', {
			parent : 'study',
			url : '/refund-policy',
			data : {
				authorities : [],
				pageTitle : 'Refund Policy'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'RefundPolicyController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'RefundPolicyMsg',
						function($stateParams, RefundPolicyMsg) {
							return RefundPolicyMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
