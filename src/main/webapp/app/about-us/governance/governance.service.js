(function() {
	'use strict';
	angular.module('miuApp').factory('GovernanceMsg', GovernanceMsg);

	GovernanceMsg.$inject = [ '$resource' ];

	function GovernanceMsg($resource) {
		var resourceUrl = 'api/public/governance';

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
