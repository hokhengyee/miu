(function() {
	'use strict';
	angular.module('miuApp')
			.factory('PublicResearchPaper', PublicResearchPaper);

	PublicResearchPaper.$inject = [ '$resource' ];

	function PublicResearchPaper($resource) {
		var resourceUrl = 'api/public/course/:id/research-papers';

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
