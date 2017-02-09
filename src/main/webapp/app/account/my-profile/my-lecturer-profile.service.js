(function() {
	'use strict';

	angular.module('miuApp').factory('MyLecturerProfile', MyLecturerProfile);

	MyLecturerProfile.$inject = [ '$resource', 'DateUtils' ];

	function MyLecturerProfile($resource, DateUtils) {
		var service = $resource('api/my-lecturer-profiles', {}, {
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

					else {
						data = null;
					}

					return data;
				}
			},
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateOfBirth = DateUtils.convertLocalDateToServer(copy.dateOfBirth);
                    copy.applicationDate = DateUtils.convertLocalDateToServer(copy.applicationDate);
                    copy.commencementDate = DateUtils.convertLocalDateToServer(copy.commencementDate);
                    copy.completionDate = DateUtils.convertLocalDateToServer(copy.completionDate);
                    return angular.toJson(copy);
                }
            }
		});

		return service;
	}
})();
