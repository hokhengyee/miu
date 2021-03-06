(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('LecturerProfile', LecturerProfile);

    LecturerProfile.$inject = ['$resource'];

    function LecturerProfile ($resource) {
        var resourceUrl =  'api/lecturer-profiles/:id';

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
