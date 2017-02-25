(function() {
	'use strict';
	angular.module('miuApp').factory('PublicAdjunctFacultyMsg',
			PublicAdjunctFacultyMsg);

	PublicAdjunctFacultyMsg.$inject = [ '$resource' ];

	function PublicAdjunctFacultyMsg($resource) {
		var resourceUrl = 'api/public/adjunct-faculty';

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
