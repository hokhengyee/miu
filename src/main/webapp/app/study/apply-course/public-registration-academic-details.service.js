(function() {
	'use strict';
	angular.module('miuApp').factory('PublicRegistrationAcademicDetails',
			PublicRegistrationAcademicDetails);

	PublicRegistrationAcademicDetails.$inject = [ '$resource' ];

	function PublicRegistrationAcademicDetails($resource) {
		var resourceUrl = 'api/public/registration-academic-details/:id';

		return $resource(resourceUrl, {}, {
			'update' : {
				method : 'PUT'
			}
		});
	}
})();
