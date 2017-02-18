(function() {
	'use strict';
	angular.module('miuApp').factory('MyBookReviewResult', MyBookReviewResult);

	MyBookReviewResult.$inject = [ '$resource' ];

	function MyBookReviewResult($resource) {
		var resourceUrl = 'api/my-book-reviews-results';

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
