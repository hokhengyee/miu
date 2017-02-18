(function() {
	'use strict';
	angular.module('miuApp').factory('MyDissertationResult',
			MyDissertationResult);

	MyDissertationResult.$inject = [ '$resource' ];

	function MyDissertationResult($resource) {
		var resourceUrl = 'api/my-dissertation-results';

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
