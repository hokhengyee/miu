(function() {
	'use strict';
	angular.module('miuApp').factory('MySermonResult', MySermonResult);

	MySermonResult.$inject = [ '$resource' ];

	function MySermonResult($resource) {
		var resourceUrl = 'api/my-sermon-results';

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
