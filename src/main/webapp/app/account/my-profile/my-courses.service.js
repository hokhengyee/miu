(function() {
	'use strict';
	angular.module('miuApp').factory('MyCourseAccess', MyCourseAccess);

	MyCourseAccess.$inject = [ '$resource' ];

	function MyCourseAccess($resource) {
		var resourceUrl = 'api/my-courses';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				isArray : true,
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
