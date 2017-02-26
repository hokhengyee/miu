(function() {
	'use strict';

	angular.module('miuApp').factory('AllUser', AllUser);

	AllUser.$inject = [ '$resource' ];

	function AllUser($resource) {
		var resourceUrl = 'api/all-users';

		var service = $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					data = angular.fromJson(data);
					return data;
				}
			}
		});

		return service;
	}
})();
