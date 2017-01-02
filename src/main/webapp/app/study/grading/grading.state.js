(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('grading-msg', {
			parent : 'study',
			url : '/grading',
			data : {
				authorities : [],
				pageTitle : 'Grading'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'GradingController',
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
