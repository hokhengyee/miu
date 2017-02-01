(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('RecordOfCertificateDetailController', RecordOfCertificateDetailController);

    RecordOfCertificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'RecordOfCertificate'];

    function RecordOfCertificateDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, RecordOfCertificate) {
        var vm = this;

        vm.recordOfCertificate = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:recordOfCertificateUpdate', function(event, result) {
            vm.recordOfCertificate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
