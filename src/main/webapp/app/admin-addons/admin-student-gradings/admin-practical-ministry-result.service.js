(function() {
	'use strict';
	angular.module('miuApp').factory('AdminPracticalMinistryResult',
			AdminPracticalMinistryResult);

	AdminPracticalMinistryResult.$inject = [ '$resource' ];

	function AdminPracticalMinistryResult($resource) {
		var resourceUrl = 'api/admin-find-user-practical-ministry-modules-results/:id';

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
