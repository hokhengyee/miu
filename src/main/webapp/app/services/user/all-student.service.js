(function() {
	'use strict';

	angular.module('miuApp').factory('StudentUser', StudentUser);

	StudentUser.$inject = [ '$resource' ];

	function StudentUser($resource) {
		var resourceUrl = 'api/student-users';

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
