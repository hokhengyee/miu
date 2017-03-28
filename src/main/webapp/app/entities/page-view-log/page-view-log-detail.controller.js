(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('PageViewLogDetailController', PageViewLogDetailController);

    PageViewLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PageViewLog'];

    function PageViewLogDetailController($scope, $rootScope, $stateParams, previousState, entity, PageViewLog) {
        var vm = this;

        vm.pageViewLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:pageViewLogUpdate', function(event, result) {
            vm.pageViewLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
