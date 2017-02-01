(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('ForumRoom', ForumRoom);

    ForumRoom.$inject = ['$resource'];

    function ForumRoom ($resource) {
        var resourceUrl =  'api/forum-rooms/:id';

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
