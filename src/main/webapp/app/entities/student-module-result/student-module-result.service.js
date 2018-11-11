(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('StudentModuleResult', StudentModuleResult);

    StudentModuleResult.$inject = ['$resource', 'DateUtils'];

    function StudentModuleResult ($resource, DateUtils) {
        var resourceUrl =  'api/student-module-results/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateGraded = DateUtils.convertLocalDateFromServer(data.dateGraded);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateGraded = DateUtils.convertLocalDateToServer(copy.dateGraded);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateGraded = DateUtils.convertLocalDateToServer(copy.dateGraded);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
