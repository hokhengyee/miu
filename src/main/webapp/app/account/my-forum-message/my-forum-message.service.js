(function() {
	'use strict';
	angular.module('miuApp').factory('MyForumMsg', MyForumMsg);

	MyForumMsg.$inject = [ '$resource' ];

	function MyForumMsg($resource) {
		var resourceUrl = 'api/forum/message/:id';

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
			'save' : {
				method : 'POST'
			}
		});
	}
})();
