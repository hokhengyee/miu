(function() {
	'use strict';
	angular.module('miuApp').factory('AdminStudentPayments',
			AdminStudentPayments);

	AdminStudentPayments.$inject = [ '$resource', 'DateUtils' ];

	function AdminStudentPayments($resource, DateUtils) {
		var resourceUrl = 'api/admin-student-payments/:id';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					return data;
				}
			}
		});
	}
})();
