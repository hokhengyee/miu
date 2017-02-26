(function() {
	'use strict';
	angular.module('miuApp').factory('MyCommonResources', MyCommonResources);

	MyCommonResources.$inject = [ '$resource' ];

	function MyCommonResources($resource) {
		var resourceUrl = 'api/ordered-common-resources';

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
