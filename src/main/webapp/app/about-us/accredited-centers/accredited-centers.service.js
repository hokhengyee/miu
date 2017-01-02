(function() {
	'use strict';
	angular.module('miuApp').factory('AccreditedCentersMsg',
			AccreditedCentersMsg);

	AccreditedCentersMsg.$inject = [ '$resource' ];

	function AccreditedCentersMsg($resource) {
		var resourceUrl = 'api/public/accredited-centers';

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
