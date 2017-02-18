(function() {
	'use strict';
	angular.module('miuApp')
			.factory('MyTheologicalResult', MyTheologicalResult);

	MyTheologicalResult.$inject = [ '$resource' ];

	function MyTheologicalResult($resource) {
		var resourceUrl = 'api/my-theological-modules-results';

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
