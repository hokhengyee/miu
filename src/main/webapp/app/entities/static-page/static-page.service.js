(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('StaticPage', StaticPage);

    StaticPage.$inject = ['$resource'];

    function StaticPage ($resource) {
        var resourceUrl =  'api/static-pages/:id';

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
