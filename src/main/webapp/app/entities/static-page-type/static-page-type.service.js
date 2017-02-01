(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('StaticPageType', StaticPageType);

    StaticPageType.$inject = ['$resource'];

    function StaticPageType ($resource) {
        var resourceUrl =  'api/static-page-types/:id';

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
