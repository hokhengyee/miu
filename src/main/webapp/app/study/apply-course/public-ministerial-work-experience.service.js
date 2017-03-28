(function() {
	'use strict';
	angular.module('miuApp').factory('PublicMinisterialWorkExperience',
			PublicMinisterialWorkExperience);

	PublicMinisterialWorkExperience.$inject = [ '$resource' ];

	function PublicMinisterialWorkExperience($resource) {
		var resourceUrl = 'api/public/ministerial-work-experiences/:id';

		return $resource(resourceUrl, {}, {
			'update' : {
				method : 'PUT'
			}
		});
	}
})();
