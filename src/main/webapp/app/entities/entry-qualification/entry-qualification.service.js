(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('EntryQualification', EntryQualification);

    EntryQualification.$inject = ['$resource'];

    function EntryQualification ($resource) {
        var resourceUrl =  'api/entry-qualifications/:id';

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
