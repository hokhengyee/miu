(function() {
	'use strict';
	angular.module('miuApp').factory('PresidentMsg', PresidentMsg);

	PresidentMsg.$inject = [ '$resource' ];

	function PresidentMsg($resource) {
		var resourceUrl = 'api/public/message-from-president';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
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
