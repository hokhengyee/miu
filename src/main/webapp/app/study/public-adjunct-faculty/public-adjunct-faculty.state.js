(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('public-adjunct-faculty', {
			parent : 'study',
			url : '/public-adjunct-faculty',
			data : {
				authorities : [],
				pageTitle : 'Adjunct Faculty'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'PublicAdjunctFacultyController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'PublicAdjunctFacultyMsg',
						function($stateParams, PublicAdjunctFacultyMsg) {
							return PublicAdjunctFacultyMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
