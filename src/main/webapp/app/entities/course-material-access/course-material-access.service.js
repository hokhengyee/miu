(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('CourseMaterialAccess', CourseMaterialAccess);

    CourseMaterialAccess.$inject = ['$resource'];

    function CourseMaterialAccess ($resource) {
        var resourceUrl =  'api/course-material-accesses/:id';

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
