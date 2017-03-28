(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('AcademicCertificateDetailController', AcademicCertificateDetailController);

    AcademicCertificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'AcademicCertificate'];

    function AcademicCertificateDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, AcademicCertificate) {
        var vm = this;

        vm.academicCertificate = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:academicCertificateUpdate', function(event, result) {
            vm.academicCertificate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
