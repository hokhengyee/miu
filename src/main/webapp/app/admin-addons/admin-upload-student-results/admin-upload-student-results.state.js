(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'admin-upload-student-results',
						{
							parent : 'entity',
							url : '/upload/student-results',
							data : {
								authorities : [ 'ROLE_ADMIN' ],
								pageTitle : 'Upload Student Results'
							},
							views : {
								'content@' : {
									templateUrl : 'app/admin-addons/admin-upload-student-results/admin-upload-student-results.html',
									controller : 'AdminUploadStudentResultsController',
									controllerAs : 'vm'
								}
							},
							resolve : {
								entity : [
										'$stateParams',
										'AdminUploadStudentResults',
										function($stateParams,
												AdminUploadStudentResults) {
											return AdminUploadStudentResults
													.get().$promise;
										} ]
							}
						})
	}

})();
