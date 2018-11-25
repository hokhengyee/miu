(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('NewsAndEventDetailController', NewsAndEventDetailController);

    NewsAndEventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'NewsAndEvent'];

    function NewsAndEventDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, NewsAndEvent) {
        var vm = this;

        vm.newsAndEvent = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:newsAndEventUpdate', function(event, result) {
            vm.newsAndEvent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
