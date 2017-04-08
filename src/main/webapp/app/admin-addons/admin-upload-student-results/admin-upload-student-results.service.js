(function() {
	'use strict';
	angular.module('miuApp').factory('AdminUploadStudentResults',
			AdminUploadStudentResults);

	AdminUploadStudentResults.$inject = [ '$resource' ];

	function AdminUploadStudentResults($resource) {
		var resourceUrl = '/api/load-data/student-results';

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
