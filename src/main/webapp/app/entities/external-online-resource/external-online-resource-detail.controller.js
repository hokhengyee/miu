(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ExternalOnlineResourceDetailController', ExternalOnlineResourceDetailController);

    ExternalOnlineResourceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ExternalOnlineResource'];

    function ExternalOnlineResourceDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ExternalOnlineResource) {
        var vm = this;

        vm.externalOnlineResource = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:externalOnlineResourceUpdate', function(event, result) {
            vm.externalOnlineResource = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
