(function() {
	'use strict';
	angular.module('miuApp').factory('AdminUploadLecProf', AdminUploadLecProf);

	AdminUploadLecProf.$inject = [ '$resource' ];

	function AdminUploadLecProf($resource) {
		var resourceUrl = '/api/load-data/lecturer-profile';

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
