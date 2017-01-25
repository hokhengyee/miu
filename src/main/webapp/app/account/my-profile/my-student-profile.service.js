(function() {
	'use strict';

	angular.module('miuApp').factory('MyStudentProfile', MyStudentProfile);

	MyStudentProfile.$inject = [ '$resource' ];

	function MyStudentProfile($resource) {
		var service = $resource('api/my-student-profiles', {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					data = angular.fromJson(data);
					return data;
				}
			}
		});

		return service;
	}
})();
