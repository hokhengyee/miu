(function() {
	'use strict';
	angular.module('miuApp').factory('PublicMinisterialWorkExperience',
			PublicMinisterialWorkExperience);

	PublicMinisterialWorkExperience.$inject = [ '$resource' ];

	function PublicMinisterialWorkExperience($resource) {
		var resourceUrl = 'api/public/ministerial-work-experiences/:id';

		return $resource(resourceUrl, {}, {
			// 'query': { method: 'GET', isArray: true},
			// 'get': {
			// method: 'GET',
			// transformResponse: function (data) {
			// if (data) {
			// data = angular.fromJson(data);
			// }
			// return data;
			// }
			// },
			'update' : {
				method : 'PUT'
			}
		});
	}
})();
