(function() {
	'use strict';
	angular.module('miuApp').factory('PublicOnlineApplication', PublicOnlineApplication);

	PublicOnlineApplication.$inject = [ '$resource', 'DateUtils' ];

	function PublicOnlineApplication($resource, DateUtils) {
		var resourceUrl = 'api/public/online-applications/:id';

		return $resource(
				resourceUrl,
				{},
				{
					'query' : {
						method : 'GET',
						isArray : true
					},
					'get' : {
						method : 'GET',
						transformResponse : function(data) {
							if (data) {
								data = angular.fromJson(data);
								data.dateOfBirth = DateUtils
										.convertLocalDateFromServer(data.dateOfBirth);
								data.registrationDatetime = DateUtils
										.convertDateTimeFromServer(data.registrationDatetime);
							}
							return data;
						}
					},
					'save' : {
						method : 'POST',
						transformRequest : function(data) {
							var copy = angular.copy(data);
							copy.dateOfBirth = DateUtils
									.convertLocalDateToServer(copy.dateOfBirth);
							return angular.toJson(copy);
						}
					}
				});
	}
})();
