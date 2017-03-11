(function() {
	'use strict';
	angular.module('miuApp').factory('OAAcademicDetails', OAAcademicDetails);

	OAAcademicDetails.$inject = [ '$resource' ];

	function OAAcademicDetails($resource) {
		var resourceUrl = 'api/find-registration-academic-details-by-md5/:md5key';

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
