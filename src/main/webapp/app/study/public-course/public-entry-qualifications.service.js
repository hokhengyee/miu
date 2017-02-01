(function() {
	'use strict';
	angular.module('miuApp').factory('PublicEntryQualification',
			PublicEntryQualification);

	PublicEntryQualification.$inject = [ '$resource' ];

	function PublicEntryQualification($resource) {
		var resourceUrl = 'api/public/course/:id/entry-qualifications';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET'
			},
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
