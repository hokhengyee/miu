(function() {
	'use strict';
	angular.module('miuApp').factory('HomeNewsAndEvents', HomeNewsAndEvents);

	HomeNewsAndEvents.$inject = [ '$resource', 'DateUtils' ];

	function HomeNewsAndEvents($resource, DateUtils) {
		var resourceUrl = 'api/public/news-and-events-top3';

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
						data.startDate = DateUtils
								.convertLocalDateFromServer(data.startDate);
						data.endDate = DateUtils
								.convertLocalDateFromServer(data.endDate);
					}
					return data;
				}
			}
		});
	}
})();
