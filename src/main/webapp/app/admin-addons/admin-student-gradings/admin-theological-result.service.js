(function() {
	'use strict';
	angular.module('miuApp')
			.factory('AdminTheologicalResult', AdminTheologicalResult);

	AdminTheologicalResult.$inject = [ '$resource' ];

	function AdminTheologicalResult($resource) {
		var resourceUrl = 'api/admin-find-user-theological-modules-results/:id';

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
