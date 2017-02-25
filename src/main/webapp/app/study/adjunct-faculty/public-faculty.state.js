(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('adjunct-faculty', {
			parent : 'study',
			url : '/adjunct-faculty',
			data : {
				authorities : [],
				pageTitle : 'Adjunct Faculty'
			},
			views : {
				'content@' : {
					templateUrl : 'app/static/public-static-page.html',
					controller : 'FacultyAlumniController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				entity : [ '$stateParams', 'FacultyAlumniMsg',
						function($stateParams, FacultyAlumniMsg) {
							return FacultyAlumniMsg.get({
								id : $stateParams.id
							}).$promise;
						} ]
			}
		});
	}
})();
