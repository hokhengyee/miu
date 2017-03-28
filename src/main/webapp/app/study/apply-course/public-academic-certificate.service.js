(function() {
	'use strict';
	angular.module('miuApp').factory('PublicAcademicCertificate',
			PublicAcademicCertificate);

	PublicAcademicCertificate.$inject = [ '$resource' ];

	function PublicAcademicCertificate($resource) {
		var resourceUrl = 'api/public/academic-certificates/:id';

		return $resource(resourceUrl, {}, {
			'update' : {
				method : 'PUT'
			}
		});
	}
})();
