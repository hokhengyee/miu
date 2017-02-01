(function() {
	'use strict';
	angular.module('miuApp').factory('HomeMsg', HomeMsg);

	HomeMsg.$inject = [ '$resource' ];

	function HomeMsg($resource) {
		var resourceUrl = 'api/public/home';

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
