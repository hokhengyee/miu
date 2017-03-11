(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('MinisterialWorkExperience', MinisterialWorkExperience);

    MinisterialWorkExperience.$inject = ['$resource'];

    function MinisterialWorkExperience ($resource) {
        var resourceUrl =  'api/ministerial-work-experiences/:id';

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
