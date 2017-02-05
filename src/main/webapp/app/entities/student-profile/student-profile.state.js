(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-profile', {
            parent: 'entity',
            url: '/student-profile?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'StudentProfiles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-profile/student-profiles.html',
                    controller: 'StudentProfileController',
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
        .state('student-profile-detail', {
            parent: 'entity',
            url: '/student-profile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StudentProfile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-profile/student-profile-detail.html',
                    controller: 'StudentProfileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StudentProfile', function($stateParams, StudentProfile) {
                    return StudentProfile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-profile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-profile-detail.edit', {
            parent: 'student-profile-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-profile/student-profile-dialog.html',
                    controller: 'StudentProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentProfile', function(StudentProfile) {
                            return StudentProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-profile.new', {
            parent: 'student-profile',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-profile/student-profile-dialog.html',
                    controller: 'StudentProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                studentId: null,
                                dateOfBirth: null,
                                phone: null,
                                applicationDate: null,
                                commencementDate: null,
                                completionDate: null,
                                mailingAddress: null,
                                profilePhoto: null,
                                profilePhotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-profile', null, { reload: 'student-profile' });
                }, function() {
                    $state.go('student-profile');
                });
            }]
        })
        .state('student-profile.edit', {
            parent: 'student-profile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-profile/student-profile-dialog.html',
                    controller: 'StudentProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentProfile', function(StudentProfile) {
                            return StudentProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-profile', null, { reload: 'student-profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-profile.delete', {
            parent: 'student-profile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-profile/student-profile-delete-dialog.html',
                    controller: 'StudentProfileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentProfile', function(StudentProfile) {
                            return StudentProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-profile', null, { reload: 'student-profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
