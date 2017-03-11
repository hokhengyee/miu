(function() {
	'use strict';
	angular.module('miuApp').factory('PublicRegistrationAcademicDetails',
			PublicRegistrationAcademicDetails);

	PublicRegistrationAcademicDetails.$inject = [ '$resource' ];

	function PublicRegistrationAcademicDetails($resource) {
		var resourceUrl = 'api/public/registration-academic-details/:id';

		return $resource(resourceUrl, {}, {
//			'query' : {
//				method : 'GET',
//				isArray : true
//			},
//			'get' : {
//				method : 'GET',
//				transformResponse : function(data) {
//					if (data) {
//						data = angular.fromJson(data);
//					}
//					return data;
//				}
//			},
			'update' : {
				method : 'PUT'
			}
		});
	}
})();
