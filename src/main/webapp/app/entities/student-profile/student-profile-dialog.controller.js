(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentProfileDialogController', StudentProfileDialogController);

    StudentProfileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'StudentProfile', 'Salutation', 'Gender', 'User'];

    function StudentProfileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, StudentProfile, Salutation, Gender, User) {
        var vm = this;

        vm.studentProfile = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.salutations = Salutation.query();
        vm.genders = Gender.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.studentProfile.id !== null) {
                StudentProfile.update(vm.studentProfile, onSaveSuccess, onSaveError);
            } else {
                StudentProfile.save(vm.studentProfile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('miuApp:studentProfileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateOfBirth = false;
        vm.datePickerOpenStatus.applicationDate = false;
        vm.datePickerOpenStatus.commencementDate = false;
        vm.datePickerOpenStatus.completionDate = false;

        vm.setProfilePhoto = function ($file, studentProfile) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        studentProfile.profilePhoto = base64Data;
                        studentProfile.profilePhotoContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.extendedCompletionDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
