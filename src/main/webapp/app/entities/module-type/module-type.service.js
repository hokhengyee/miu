(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('ModuleType', ModuleType);

    ModuleType.$inject = ['$resource'];

    function ModuleType ($resource) {
        var resourceUrl =  'api/module-types/:id';

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
