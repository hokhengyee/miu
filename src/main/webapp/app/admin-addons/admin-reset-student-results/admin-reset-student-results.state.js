(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'admin-reset-student-results',
						{
							parent : 'entity',
							url : '/reset/student-results',
							data : {
								authorities : [ 'ROLE_ADMIN' ],
								pageTitle : 'Upload Student Results'
							},
							views : {
								'content@' : {
									templateUrl : 'app/admin-addons/admin-reset-student-results/admin-reset-student-results.html',
									controller : 'AdminResetStudentResultsController',
									controllerAs : 'vm'
								}
							},
							resolve : {
								entity : [
										'$stateParams',
										'AdminResetStudentResults',
										function($stateParams,
												AdminResetStudentResults) {
											return AdminResetStudentResults
													.get().$promise;
										} ]
							}
						})
	}

})();
