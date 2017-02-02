(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('my-profile', {
            parent: 'account',
            url: '/my-profile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'My Profile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/my-profile/my-profile.html',
                    controller: 'MyProfileController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('my-profile.edit', {
            parent: 'account',
            url: '/my-profile/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/account/my-profile/my-profile-dialog.html',
                    controller: 'MyProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyStudentProfile', function(MyStudentProfile) {
                            return MyStudentProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-profile', {}, { reload: false });
                }, function() {
                    $state.go('my-profile');
                });
            }]
        });
    }
})();
