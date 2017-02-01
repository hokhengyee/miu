(function() {
	'use strict';

	angular.module('miuApp').factory('MyStudentProfile', MyStudentProfile);

	MyStudentProfile.$inject = [ '$resource', 'DateUtils' ];

	function MyStudentProfile($resource, DateUtils) {
		var service = $resource('api/my-student-profiles', {}, {
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
								.convertDateTimeFromServer(data.dateOfBirth);
						data.applicationDate = DateUtils
								.convertLocalDateFromServer(data.applicationDate);
						data.commencementDate = DateUtils
								.convertLocalDateFromServer(data.commencementDate);
						data.completionDate = DateUtils
								.convertLocalDateFromServer(data.completionDate);
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
