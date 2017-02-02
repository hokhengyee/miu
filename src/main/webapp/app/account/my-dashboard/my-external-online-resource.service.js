(function() {
	'use strict';
	angular.module('miuApp').factory('MyExternalOnlineResource',
			MyExternalOnlineResource);

	MyExternalOnlineResource.$inject = [ '$resource' ];

	function MyExternalOnlineResource($resource) {
		var resourceUrl = 'api/external-online-resources/:id';

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
