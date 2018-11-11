(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('RegistrationAcademicDetails', RegistrationAcademicDetails);

    RegistrationAcademicDetails.$inject = ['$resource'];

    function RegistrationAcademicDetails ($resource) {
        var resourceUrl =  'api/registration-academic-details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
