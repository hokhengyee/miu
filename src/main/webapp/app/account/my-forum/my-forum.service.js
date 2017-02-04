(function() {
	'use strict';
	angular.module('miuApp').factory('MyForumRoom', MyForumRoom);

	MyForumRoom.$inject = [ '$resource' ];

	function MyForumRoom($resource) {
		var resourceUrl = 'api/forum-rooms/:id';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					if (data) {
						data = angular.fromJson(data);
					}
					return data;
				}
			},
			'update' : {
				method : 'PUT'
			}
		});
	}
})();
