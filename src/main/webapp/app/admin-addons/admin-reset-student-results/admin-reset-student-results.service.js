(function() {
	'use strict';
	angular.module('miuApp').factory('AdminResetStudentResults',
			AdminResetStudentResults);

	AdminResetStudentResults.$inject = [ '$resource' ];

	function AdminResetStudentResults($resource) {
		var resourceUrl = '/api/load-data/student-results/reset';

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
