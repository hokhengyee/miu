(function() {
	'use strict';
	angular.module('miuApp').factory('SOFMsg', SOFMsg);

	SOFMsg.$inject = [ '$resource' ];

	function SOFMsg($resource) {
		var resourceUrl = 'api/public/statement-of-faith';

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
