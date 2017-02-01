(function() {
	'use strict';
	angular.module('miuApp').factory('MyPayment', MyPayment);

	MyPayment.$inject = [ '$resource', 'DateUtils' ];

	function MyPayment($resource, DateUtils) {
		var resourceUrl = 'api/my-payments';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET',
				isArray : true,
				transformResponse : function(data) {
//					if (data) {
//						data = angular.fromJson(data);
//						data.createdDate = DateUtils
//								.convertDateTimeFromServer(data.createdDate);
//						data.paymentDate = DateUtils
//								.convertLocalDateFromServer(data.paymentDate);
//					}
					return data;
				}
			}
		});
	}
})();
