(function() {
	'use strict';
	angular.module('miuApp').factory('GradingMsg', GradingMsg);

	GradingMsg.$inject = [ '$resource' ];

	function GradingMsg($resource) {
		var resourceUrl = 'api/public/grading';

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
