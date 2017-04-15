(function() {
	'use strict';
	angular.module('miuApp').factory('AlumniMsg', AlumniMsg);

	AlumniMsg.$inject = [ '$resource' ];

	function AlumniMsg($resource) {
		var resourceUrl = 'api/public/alumni';

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
