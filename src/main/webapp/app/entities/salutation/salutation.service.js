(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('Salutation', Salutation);

    Salutation.$inject = ['$resource'];

    function Salutation ($resource) {
        var resourceUrl =  'api/salutations/:id';

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
