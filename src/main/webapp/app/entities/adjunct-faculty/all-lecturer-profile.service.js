(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('AllLecturerProfile', AllLecturerProfile);

    AllLecturerProfile.$inject = ['$resource'];

    function AllLecturerProfile ($resource) {
        var resourceUrl =  'api/all-lecturer-profiles';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
