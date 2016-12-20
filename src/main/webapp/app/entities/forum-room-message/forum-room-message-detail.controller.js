(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ForumRoomMessageDetailController', ForumRoomMessageDetailController);

    ForumRoomMessageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ForumRoomMessage', 'ForumRoom', 'User'];

    function ForumRoomMessageDetailController($scope, $rootScope, $stateParams, previousState, entity, ForumRoomMessage, ForumRoom, User) {
        var vm = this;

        vm.forumRoomMessage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:forumRoomMessageUpdate', function(event, result) {
            vm.forumRoomMessage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
