(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('GalleryDialogController', GalleryDialogController);

    GalleryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Gallery'];

    function GalleryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Gallery) {
        var vm = this;

        vm.gallery = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gallery.id !== null) {
                Gallery.update(vm.gallery, onSaveSuccess, onSaveError);
            } else {
                Gallery.save(vm.gallery, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:galleryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setGalleryPhoto = function ($file, gallery) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        gallery.galleryPhoto = base64Data;
                        gallery.galleryPhotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
