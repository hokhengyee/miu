(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('CourseMaterial', CourseMaterial);

    CourseMaterial.$inject = ['$resource'];

    function CourseMaterial ($resource) {
        var resourceUrl =  'api/course-materials/:id';

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
