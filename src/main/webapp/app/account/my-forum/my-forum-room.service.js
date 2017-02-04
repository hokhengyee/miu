(function() {
	'use strict';
	angular.module('miuApp').factory('MyForumRoomMsg', MyForumRoomMsg);

	MyForumRoomMsg.$inject = [ '$resource' ];

	function MyForumRoomMsg($resource) {
		var resourceUrl = 'api/forum/:id/messages';

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
			},
			'save' : {
				method : 'POST'
			}
		});
	}
})();
