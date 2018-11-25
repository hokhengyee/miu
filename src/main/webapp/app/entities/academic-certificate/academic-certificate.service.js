(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('AcademicCertificate', AcademicCertificate);

    AcademicCertificate.$inject = ['$resource'];

    function AcademicCertificate ($resource) {
        var resourceUrl =  'api/academic-certificates/:id';

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
