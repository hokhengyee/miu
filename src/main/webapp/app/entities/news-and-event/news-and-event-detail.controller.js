(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('NewsAndEventDetailController', NewsAndEventDetailController);

    NewsAndEventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NewsAndEvent'];

    function NewsAndEventDetailController($scope, $rootScope, $stateParams, previousState, entity, NewsAndEvent) {
        var vm = this;

        vm.newsAndEvent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:newsAndEventUpdate', function(event, result) {
            vm.newsAndEvent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
