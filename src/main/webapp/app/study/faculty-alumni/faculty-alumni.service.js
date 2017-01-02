(function() {
	'use strict';
	angular.module('miuApp').factory('FacultyAlumniMsg', FacultyAlumniMsg);

	FacultyAlumniMsg.$inject = [ '$resource' ];

	function FacultyAlumniMsg($resource) {
		var resourceUrl = 'api/public/faculty-and-alumni';

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
