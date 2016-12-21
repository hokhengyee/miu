(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('grading', {
			parent : 'study',
			url : '/grading',
			data : {
				authorities : [],
				pageTitle : 'Grading'
			},
			views : {
				'content@' : {
					templateUrl : 'app/study/grading/gradings.html',
					controller : 'GradingController',
					controllerAs : 'vm'
				}
			}
		});
	}
})();
