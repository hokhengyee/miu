(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('CommonResources', CommonResources);

    CommonResources.$inject = ['$resource'];

    function CommonResources ($resource) {
        var resourceUrl =  'api/common-resources/:id';

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
