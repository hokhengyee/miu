(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('CommonResourcesDetailController', CommonResourcesDetailController);

    CommonResourcesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CommonResources'];

    function CommonResourcesDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CommonResources) {
        var vm = this;

        vm.commonResources = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:commonResourcesUpdate', function(event, result) {
            vm.commonResources = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
