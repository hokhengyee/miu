(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'admin-upload-roc',
						{
							parent : 'entity',
							url : '/upload/record-of-certificate',
							data : {
								authorities : [ 'ROLE_ADMIN' ],
								pageTitle : 'Upload Record Of Certificates'
							},
							views : {
								'content@' : {
									templateUrl : 'app/admin-addons/admin-upload-roc/admin-upload-roc.html',
									controller : 'AdminUploadROCController',
									controllerAs : 'vm'
								}
							},
							resolve : {
								entity : [
										'$stateParams',
										'AdminUploadROC',
										function($stateParams, AdminUploadROC) {
											return AdminUploadROC.get().$promise;
										} ]
							}
						})
	}

})();
