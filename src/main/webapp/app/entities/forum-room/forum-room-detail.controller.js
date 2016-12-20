(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ForumRoomDetailController', ForumRoomDetailController);

    ForumRoomDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ForumRoom'];

    function ForumRoomDetailController($scope, $rootScope, $stateParams, previousState, entity, ForumRoom) {
        var vm = this;

        vm.forumRoom = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:forumRoomUpdate', function(event, result) {
            vm.forumRoom = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
