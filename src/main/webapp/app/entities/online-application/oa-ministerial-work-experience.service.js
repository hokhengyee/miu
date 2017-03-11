(function() {
	'use strict';
	angular.module('miuApp').factory('OAMinisterialWorkExperience',
			OAMinisterialWorkExperience);

	OAMinisterialWorkExperience.$inject = [ '$resource' ];

	function OAMinisterialWorkExperience($resource) {
		var resourceUrl = 'api/find-ministerial-work-experiences-by-md5/:md5key';

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
