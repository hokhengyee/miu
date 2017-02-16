(function() {
	'use strict';
	angular.module('miuApp').factory('AUNewsAndEvent', AUNewsAndEvent);

	AUNewsAndEvent.$inject = [ '$resource', 'DateUtils' ];

	function AUNewsAndEvent($resource, DateUtils) {
		var resourceUrl = 'api/public/news-and-events/:id';

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
						data.startDT = DateUtils
								.convertDateTimeFromServer(data.startDT);
						data.endDT = DateUtils
								.convertDateTimeFromServer(data.endDT);
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
