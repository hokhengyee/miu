(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider.state('admin-student-gradings', {
			parent : 'admin-addons',
			url : '/admin-student-gradings/{id}',
			data : {
				authorities : [ 'ROLE_ADMIN' ],
				pageTitle : 'Student Gradings'
			},
			views : {
				'content@' : {
					templateUrl : 'app/admin-addons/admin-student-gradings/admin-student-gradings.html',
					controller : 'AdminStudentGradingsController',
					controllerAs : 'vm'
				}
			},
			resolve : {
				previousState : [
					"$state",
					function($state) {
						var currentStateData = {
							name : $state.current.name
									|| 'student-profile',
							params : $state.params,
							url : $state.href(
									$state.current.name,
									$state.params)
						};
						return currentStateData;
					} ]
			}
		});
	}
})();
