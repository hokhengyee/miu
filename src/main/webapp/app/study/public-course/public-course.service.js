(function() {
	'use strict';
	angular.module('miuApp').factory('PublicCourse', PublicCourse);

	PublicCourse.$inject = [ '$resource' ];

	function PublicCourse($resource) {
		var resourceUrl = 'api/public/courses/:id';

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
