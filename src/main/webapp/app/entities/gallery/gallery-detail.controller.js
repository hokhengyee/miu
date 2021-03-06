(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('GalleryDetailController', GalleryDetailController);

    GalleryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Gallery'];

    function GalleryDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Gallery) {
        var vm = this;

        vm.gallery = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('miuApp:galleryUpdate', function(event, result) {
            vm.gallery = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
