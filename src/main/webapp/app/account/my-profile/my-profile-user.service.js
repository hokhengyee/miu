(function() {
	'use strict';

	angular.module('miuApp').factory('MyProfileUser', MyProfileUser);

	MyProfileUser.$inject = [ '$resource' ];

	function MyProfileUser($resource) {
		var service = $resource('api/my-profile/:login', {}, {
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
