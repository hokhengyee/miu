(function() {
	'use strict';
	angular.module('miuApp').factory('MyPracticalMinistryResult',
			MyPracticalMinistryResult);

	MyPracticalMinistryResult.$inject = [ '$resource' ];

	function MyPracticalMinistryResult($resource) {
		var resourceUrl = 'api/my-practical-ministry-modules-results';

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
