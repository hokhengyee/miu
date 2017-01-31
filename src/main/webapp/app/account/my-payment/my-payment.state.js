(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('my-payments', {
			parent : 'account',
			url : '/my-payments',
			data : {
				authorities : [ 'ROLE_USER', 'ROLE_STUDENT' ],
				pageTitle : 'StudentPayments'
			},
			views : {
				'content@' : {
					templateUrl : 'app/account/my-payment/my-payments.html',
					controller : 'MyPaymentController',
					controllerAs : 'vm'
				}
			}
		});
	}

})();
