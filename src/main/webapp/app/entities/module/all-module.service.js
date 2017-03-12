(function() {
	'use strict';
	angular.module('miuApp').factory('AllModule', AllModule);

	AllModule.$inject = [ '$resource' ];

	function AllModule($resource) {
		var resourceUrl = 'api/all-modules';

		return $resource(resourceUrl, {}, {
			'get' : {
				method : 'GET',
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
