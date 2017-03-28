(function() {
	'use strict';
	angular.module('miuApp').factory('OAAcademicCertificate',
			OAAcademicCertificate);

	OAAcademicCertificate.$inject = [ '$resource' ];

	function OAAcademicCertificate($resource) {
		var resourceUrl = 'api/find-academic-certificates-by-md5/:md5key';

		return $resource(resourceUrl, {}, {
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					if (data) {
						data = angular.fromJson(data);
					}
					return data;
				}
			}
		});
	}
})();
