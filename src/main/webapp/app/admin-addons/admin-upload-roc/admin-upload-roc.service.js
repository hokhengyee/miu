(function() {
	'use strict';
	angular.module('miuApp').factory('AdminUploadROC', AdminUploadROC);

	AdminUploadROC.$inject = [ '$resource' ];

	function AdminUploadROC($resource) {
		var resourceUrl = '/api/load-data/record-of-certificates';

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
