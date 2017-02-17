(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('CustomStudentReportType', CustomStudentReportType);

    CustomStudentReportType.$inject = ['$resource'];

    function CustomStudentReportType ($resource) {
        var resourceUrl =  'api/custom-student-report-types/:id';

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
