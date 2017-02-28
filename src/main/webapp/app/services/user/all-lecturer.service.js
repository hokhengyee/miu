(function() {
	'use strict';

	angular.module('miuApp').factory('LecturerUser', LecturerUser);

	LecturerUser.$inject = [ '$resource' ];

	function LecturerUser($resource) {
		var resourceUrl = 'api/lecturer-users';

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
