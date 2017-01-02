(function() {
	'use strict';
	angular.module('miuApp').factory('RefundPolicyMsg', RefundPolicyMsg);

	RefundPolicyMsg.$inject = [ '$resource' ];

	function RefundPolicyMsg($resource) {
		var resourceUrl = 'api/public/refund-policy';

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
