(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('my-results', {
			parent : 'account',
			url : '/my-gradings',
			data : {
				authorities : [ 'ROLE_USER' ],
				pageTitle : 'My Gradings'
			},
			views : {
				'content@' : {
					templateUrl : 'app/account/my-result/my-result.html',
					controller : 'MyResultController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'GradingMsg',
						function($stateParams, GradingMsg) {
							return GradingMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
