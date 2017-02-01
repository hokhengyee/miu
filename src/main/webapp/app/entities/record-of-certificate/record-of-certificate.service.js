(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('RecordOfCertificate', RecordOfCertificate);

    RecordOfCertificate.$inject = ['$resource', 'DateUtils'];

    function RecordOfCertificate ($resource, DateUtils) {
        var resourceUrl =  'api/record-of-certificates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.certDate = DateUtils.convertLocalDateFromServer(data.certDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.certDate = DateUtils.convertLocalDateToServer(copy.certDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.certDate = DateUtils.convertLocalDateToServer(copy.certDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
