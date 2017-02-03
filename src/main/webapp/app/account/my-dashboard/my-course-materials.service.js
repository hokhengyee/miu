(function() {
	'use strict';
	angular.module('miuApp').factory('MyCourseMaterials', MyCourseMaterials);

	MyCourseMaterials.$inject = [ '$resource' ];

	function MyCourseMaterials($resource) {
		var resourceUrl = 'api/course/:id/course-materials';

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
