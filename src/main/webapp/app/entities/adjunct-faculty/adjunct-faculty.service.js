(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('AdjunctFaculty', AdjunctFaculty);

    AdjunctFaculty.$inject = ['$resource'];

    function AdjunctFaculty ($resource) {
        var resourceUrl =  'api/adjunct-faculties/:id';

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
