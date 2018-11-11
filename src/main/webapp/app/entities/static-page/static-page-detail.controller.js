(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StaticPageDetailController', StaticPageDetailController);

    StaticPageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StaticPage', 'StaticPageType'];

    function StaticPageDetailController($scope, $rootScope, $stateParams, previousState, entity, StaticPage, StaticPageType) {
        var vm = this;

        vm.staticPage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:staticPageUpdate', function(event, result) {
            vm.staticPage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
