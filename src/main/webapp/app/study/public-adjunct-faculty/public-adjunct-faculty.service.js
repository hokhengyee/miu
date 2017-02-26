(function() {
	'use strict';
	angular.module('miuApp').factory('PublicAdjunctFaculty',
			PublicAdjunctFaculty);

	PublicAdjunctFaculty.$inject = [ '$resource' ];

	function PublicAdjunctFaculty($resource) {
		var resourceUrl = 'api/public/adjunct-faculty';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
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
