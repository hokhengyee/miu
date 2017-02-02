(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('ExternalOnlineResource', ExternalOnlineResource);

    ExternalOnlineResource.$inject = ['$resource'];

    function ExternalOnlineResource ($resource) {
        var resourceUrl =  'api/external-online-resources/:id';

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
