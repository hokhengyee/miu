(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('NewsAndEvent', NewsAndEvent);

    NewsAndEvent.$inject = ['$resource', 'DateUtils'];

    function NewsAndEvent ($resource, DateUtils) {
        var resourceUrl =  'api/news-and-events/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDT = DateUtils.convertDateTimeFromServer(data.startDT);
                        data.endDT = DateUtils.convertDateTimeFromServer(data.endDT);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
