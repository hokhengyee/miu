(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('LecturerProfileDetailController', LecturerProfileDetailController);

    LecturerProfileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'LecturerProfile', 'User', 'Salutation'];

    function LecturerProfileDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, LecturerProfile, User, Salutation) {
        var vm = this;

        vm.lecturerProfile = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:lecturerProfileUpdate', function(event, result) {
            vm.lecturerProfile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
