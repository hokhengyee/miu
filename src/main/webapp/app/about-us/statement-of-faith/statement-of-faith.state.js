(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('statement-of-faith-msg', {
			parent : 'about-us',
			url : '/statement-of-faith',
			data : {
				authorities : [],
				pageTitle : 'Statement Of Faith'
			},
			views : {
				'content@' : {
					templateUrl : 'app/about-us/about-us.html',
					controller : 'StatementOfFaithController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'SOFMsg',
						function($stateParams, SOFMsg) {
							return SOFMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();

