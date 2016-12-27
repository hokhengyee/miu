(function() {
    'use strict';
    angular
        .module('miuApp')
        .factory('ForumRoomMessage', ForumRoomMessage);

    ForumRoomMessage.$inject = ['$resource', 'DateUtils'];

    function ForumRoomMessage ($resource, DateUtils) {
        var resourceUrl =  'api/forum-room-messages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.messageDatetime = DateUtils.convertDateTimeFromServer(data.messageDatetime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
