(function() {
	'use strict';
	angular.module('miuApp').factory('PublicGallery', PublicGallery);

	PublicGallery.$inject = [ '$resource' ];

	function PublicGallery($resource) {
		var resourceUrl = 'api/public/galleries/:id';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
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
