(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StaticPageTypeDetailController', StaticPageTypeDetailController);

    StaticPageTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StaticPageType'];

    function StaticPageTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, StaticPageType) {
        var vm = this;

        vm.staticPageType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:staticPageTypeUpdate', function(event, result) {
            vm.staticPageType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
