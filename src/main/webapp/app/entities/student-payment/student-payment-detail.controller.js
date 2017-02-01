(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentPaymentDetailController', StudentPaymentDetailController);

    StudentPaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'StudentPayment', 'User', 'Course', 'PaymentType'];

    function StudentPaymentDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, StudentPayment, User, Course, PaymentType) {
        var vm = this;

        vm.studentPayment = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:studentPaymentUpdate', function(event, result) {
            vm.studentPayment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
