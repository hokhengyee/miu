(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('OnlineApplicationDetailController', OnlineApplicationDetailController);

    OnlineApplicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'OnlineApplication', 'Course'];

    function OnlineApplicationDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, OnlineApplication, Course) {
        var vm = this;

        vm.onlineApplication = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:onlineApplicationUpdate', function(event, result) {
            vm.onlineApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
