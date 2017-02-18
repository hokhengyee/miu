(function() {
	'use strict';
	angular.module('miuApp').factory('MyArticleResult', MyArticleResult);

	MyArticleResult.$inject = [ '$resource' ];

	function MyArticleResult($resource) {
		var resourceUrl = 'api/my-articles-results';

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
