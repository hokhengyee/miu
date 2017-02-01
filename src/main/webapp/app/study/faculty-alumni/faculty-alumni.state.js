(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('faculty-alumni-msg', {
			parent : 'study',
			url : '/faculty-and-alumni',
			data : {
				authorities : [],
				pageTitle : 'Faculty & Alumni'
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
