(function() {
	'use strict';
	angular.module('miuApp').factory('AdminResearchPaperResult',
			AdminResearchPaperResult);

	AdminResearchPaperResult.$inject = [ '$resource' ];

	function AdminResearchPaperResult($resource) {
		var resourceUrl = 'api/admin-find-user-research-papers-results/:id';

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
