(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'admin-upload-lecProf',
						{
							parent : 'entity',
							url : '/upload/lecturer-profile',
							data : {
								authorities : [ 'ROLE_ADMIN' ],
								pageTitle : 'Upload Lecturer Profile'
							},
							views : {
								'content@' : {
									templateUrl : 'app/admin-addons/admin-upload-lecProf/admin-upload-lecProf.html',
									controller : 'AdminUploadLecProfController',
									controllerAs : 'vm'
								}
							},
							resolve : {
								entity : [
										'$stateParams',
										'AdminUploadLecProf',
										function($stateParams,
												AdminUploadLecProf) {
											return AdminUploadLecProf.get().$promise;
										} ]
							}
						})
	}

})();
