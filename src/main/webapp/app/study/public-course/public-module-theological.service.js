(function() {
	'use strict';
	angular.module('miuApp').factory('PublicModuleTheological',
			PublicModuleTheological);

	PublicModuleTheological.$inject = [ '$resource' ];

	function PublicModuleTheological($resource) {
		var resourceUrl = 'api/public/course/:id/theological';

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
