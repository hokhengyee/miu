(function() {
	'use strict';
	angular.module('miuApp').factory('AdminSermonResult', AdminSermonResult);

	AdminSermonResult.$inject = [ '$resource' ];

	function AdminSermonResult($resource) {
		var resourceUrl = 'api/admin-find-user-sermon-results/:id';

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
