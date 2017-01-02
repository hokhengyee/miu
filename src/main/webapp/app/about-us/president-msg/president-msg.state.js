(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('president-msg', {
			parent : 'about-us',
			url : '/message-from-the-president',
			data : {
				authorities : [],
				pageTitle : 'Message from the President'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'PresidentMsgController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'PresidentMsg',
						function($stateParams, PresidentMsg) {
							return PresidentMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
