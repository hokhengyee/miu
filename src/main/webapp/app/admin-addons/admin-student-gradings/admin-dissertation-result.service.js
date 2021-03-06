(function() {
	'use strict';
	angular.module('miuApp').factory('AdminDissertationResult',
			AdminDissertationResult);

	AdminDissertationResult.$inject = [ '$resource' ];

	function AdminDissertationResult($resource) {
		var resourceUrl = 'api/admin-find-user-dissertation-results/:id';

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
