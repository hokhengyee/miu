(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('LecturerProfileDialogController', LecturerProfileDialogController);

    LecturerProfileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'LecturerProfile', 'User', 'Salutation'];

    function LecturerProfileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, LecturerProfile, User, Salutation) {
        var vm = this;

        vm.lecturerProfile = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();
        vm.salutations = Salutation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lecturerProfile.id !== null) {
                LecturerProfile.update(vm.lecturerProfile, onSaveSuccess, onSaveError);
            } else {
                LecturerProfile.save(vm.lecturerProfile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:lecturerProfileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setProfilePhoto = function ($file, lecturerProfile) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        lecturerProfile.profilePhoto = base64Data;
                        lecturerProfile.profilePhotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
