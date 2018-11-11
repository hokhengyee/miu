(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('StudentOtherResult', StudentOtherResult);

    StudentOtherResult.$inject = ['$resource', 'DateUtils'];

    function StudentOtherResult ($resource, DateUtils) {
        var resourceUrl =  'api/student-other-results/:id';

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
