(function() {
	'use strict';
	angular.module('miuApp').factory('AdminBookReviewResult', AdminBookReviewResult);

	AdminBookReviewResult.$inject = [ '$resource' ];

	function AdminBookReviewResult($resource) {
		var resourceUrl = 'api/admin-find-user-book-reviews-results/:id';

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
