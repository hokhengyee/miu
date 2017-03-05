(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('online-application', {
            parent: 'entity',
            url: '/online-application?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'OnlineApplications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/online-application/online-applications.html',
                    controller: 'OnlineApplicationController',
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
        .state('online-application-detail', {
            parent: 'entity',
            url: '/online-application/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'OnlineApplication'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/online-application/online-application-detail.html',
                    controller: 'OnlineApplicationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OnlineApplication', function($stateParams, OnlineApplication) {
                    return OnlineApplication.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'online-application',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('online-application-detail.edit', {
            parent: 'online-application-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/online-application/online-application-dialog.html',
                    controller: 'OnlineApplicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OnlineApplication', function(OnlineApplication) {
                            return OnlineApplication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('online-application.new', {
            parent: 'online-application',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/online-application/online-application-dialog.html',
                    controller: 'OnlineApplicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateOfBirth: null,
                                telephone: null,
                                email: null,
                                city: null,
                                state: null,
                                country: null,
                                postcode: null,
                                registrationDatetime: null,
                                surname: null,
                                givenName: null,
                                address: null,
                                applicationForm: null,
                                applicationFormContentType: null,
                                profilePhoto: null,
                                profilePhotoContentType: null,
                                academicCertificate: null,
                                academicCertificateContentType: null,
                                letterOfRecommendation: null,
                                letterOfRecommendationContentType: null,
                                profileDocument: null,
                                profileDocumentContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('online-application', null, { reload: 'online-application' });
                }, function() {
                    $state.go('online-application');
                });
            }]
        })
        .state('online-application.edit', {
            parent: 'online-application',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/online-application/online-application-dialog.html',
                    controller: 'OnlineApplicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OnlineApplication', function(OnlineApplication) {
                            return OnlineApplication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('online-application', null, { reload: 'online-application' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('online-application.delete', {
            parent: 'online-application',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/online-application/online-application-delete-dialog.html',
                    controller: 'OnlineApplicationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OnlineApplication', function(OnlineApplication) {
                            return OnlineApplication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('online-application', null, { reload: 'online-application' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
