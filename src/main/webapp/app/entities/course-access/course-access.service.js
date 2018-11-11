(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('CourseAccess', CourseAccess);

    CourseAccess.$inject = ['$resource'];

    function CourseAccess ($resource) {
        var resourceUrl =  'api/course-accesses/:id';

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
