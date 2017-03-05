(function() {
	'use strict';
	angular.module('miuApp').factory('AdminArticleResult', AdminArticleResult);

	AdminArticleResult.$inject = [ '$resource' ];

	function AdminArticleResult($resource) {
		var resourceUrl = 'api/admin-find-user-articles-results/:id';

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
