(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'admin-student-payments',
						{
							parent : 'admin-addons',
							url : '/admin-student-payments/{id}',
							data : {
								authorities : [ 'ROLE_ADMIN' ],
								pageTitle : 'Student Payments'
							},
							views : {
								'content@' : {
									templateUrl : 'app/admin-addons/admin-student-payments/admin-student-payments.html',
									controller : 'AdminStudentPaymentsController',
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
