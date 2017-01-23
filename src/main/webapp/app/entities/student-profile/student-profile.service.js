(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('StudentProfile', StudentProfile);

    StudentProfile.$inject = ['$resource', 'DateUtils'];

    function StudentProfile ($resource, DateUtils) {
        var resourceUrl =  'api/student-profiles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateOfBirth = DateUtils.convertLocalDateFromServer(data.dateOfBirth);
                        data.applicationDate = DateUtils.convertLocalDateFromServer(data.applicationDate);
                        data.commencementDate = DateUtils.convertLocalDateFromServer(data.commencementDate);
                        data.completionDate = DateUtils.convertLocalDateFromServer(data.completionDate);
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
            },
            'save': {
                method: 'POST',
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
    }
})();
