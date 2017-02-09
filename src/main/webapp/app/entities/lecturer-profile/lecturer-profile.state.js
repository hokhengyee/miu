(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lecturer-profile', {
            parent: 'entity',
            url: '/lecturer-profile?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LecturerProfiles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lecturer-profile/lecturer-profiles.html',
                    controller: 'LecturerProfileController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('lecturer-profile-detail', {
            parent: 'entity',
            url: '/lecturer-profile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LecturerProfile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lecturer-profile/lecturer-profile-detail.html',
                    controller: 'LecturerProfileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LecturerProfile', function($stateParams, LecturerProfile) {
                    return LecturerProfile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lecturer-profile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lecturer-profile-detail.edit', {
            parent: 'lecturer-profile-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lecturer-profile/lecturer-profile-dialog.html',
                    controller: 'LecturerProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LecturerProfile', function(LecturerProfile) {
                            return LecturerProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lecturer-profile.new', {
            parent: 'lecturer-profile',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lecturer-profile/lecturer-profile-dialog.html',
                    controller: 'LecturerProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                otherTitles: null,
                                age: null,
                                ordination: null,
                                academicHistory: null,
                                professionalHistory: null,
                                pastAndCurrentMinistry: null,
                                publications: null,
                                familyDetails: null,
                                reference: null,
                                profilePhoto: null,
                                profilePhotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lecturer-profile', null, { reload: 'lecturer-profile' });
                }, function() {
                    $state.go('lecturer-profile');
                });
            }]
        })
        .state('lecturer-profile.edit', {
            parent: 'lecturer-profile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lecturer-profile/lecturer-profile-dialog.html',
                    controller: 'LecturerProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LecturerProfile', function(LecturerProfile) {
                            return LecturerProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lecturer-profile', null, { reload: 'lecturer-profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lecturer-profile.delete', {
            parent: 'lecturer-profile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lecturer-profile/lecturer-profile-delete-dialog.html',
                    controller: 'LecturerProfileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LecturerProfile', function(LecturerProfile) {
                            return LecturerProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lecturer-profile', null, { reload: 'lecturer-profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
