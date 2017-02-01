(function() {
	'use strict';
	angular.module('miuApp').factory('PublicModulePracticalMinistry',
			PublicModulePracticalMinistry);

	PublicModulePracticalMinistry.$inject = [ '$resource' ];

	function PublicModulePracticalMinistry($resource) {
		var resourceUrl = 'api/public/course/:id/practical-ministry';

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
