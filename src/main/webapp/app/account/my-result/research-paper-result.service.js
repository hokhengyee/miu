(function() {
	'use strict';
	angular.module('miuApp').factory('MyResearchPaperResult',
			MyResearchPaperResult);

	MyResearchPaperResult.$inject = [ '$resource' ];

	function MyResearchPaperResult($resource) {
		var resourceUrl = 'api/my-research-papers-results';

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
