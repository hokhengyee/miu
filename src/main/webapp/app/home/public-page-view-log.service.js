(function() {
	'use strict';
	angular.module('miuApp').factory('PublicPageViewLog', PublicPageViewLog);

	PublicPageViewLog.$inject = [ '$resource', 'DateUtils' ];

	function PublicPageViewLog($resource, DateUtils) {
		var resourceUrl = 'api/public/page-view-logs';

		return $resource(resourceUrl, {}, {
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					if (data) {
						data = angular.fromJson(data);
						data.createdDate = DateUtils
								.convertLocalDateFromServer(data.createdDate);
					}
					return data;
				}
			}
		});
	}
})();
