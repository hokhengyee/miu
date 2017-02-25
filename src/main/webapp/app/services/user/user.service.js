(function() {
	'use strict';

	angular.module('miuApp').factory('User', User);

	User.$inject = [ '$resource' ];

	function User($resource) {
		var resourceUrl = 'api/users/:login';

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
			},
			'save' : {
				method : 'POST'
			},
			'update' : {
				method : 'PUT'
			},
			'delete' : {
				method : 'DELETE'
			}
		});

		return service;
	}
})();
