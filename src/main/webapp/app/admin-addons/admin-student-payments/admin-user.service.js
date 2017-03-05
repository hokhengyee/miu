(function() {
	'use strict';
	angular.module('miuApp').factory('AdminFindUser', AdminFindUser);

	AdminFindUser.$inject = [ '$resource', 'DateUtils' ];

	function AdminFindUser($resource, DateUtils) {
		var resourceUrl = 'api/admin-find-user/:id';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET'
			},
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					return data;
				}
			}
		});
	}
})();
