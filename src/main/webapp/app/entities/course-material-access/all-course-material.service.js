(function () {
    'use strict';
    angular
        .module('miuApp')
        .factory('AllCourseMaterial', AllCourseMaterial);

    AllCourseMaterial.$inject = ['$resource'];

    function AllCourseMaterial($resource) {
        var resourceUrl = 'api/all-course-materials';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
