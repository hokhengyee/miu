(function() {
	'use strict';
	angular.module('miuApp').factory('PublicLecturerProfile',
			PublicLecturerProfile);

	PublicLecturerProfile.$inject = [ '$resource' ];

	function PublicLecturerProfile($resource) {
		var resourceUrl = 'api/public/public-lecturer-profiles/:id';

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
