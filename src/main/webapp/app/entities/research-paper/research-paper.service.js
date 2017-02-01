(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('ResearchPaper', ResearchPaper);

    ResearchPaper.$inject = ['$resource'];

    function ResearchPaper ($resource) {
        var resourceUrl =  'api/research-papers/:id';

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
