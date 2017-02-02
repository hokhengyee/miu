(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entry-qualification', {
            parent: 'entity',
            url: '/entry-qualification?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'EntryQualifications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entry-qualification/entry-qualifications.html',
                    controller: 'EntryQualificationController',
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
        .state('entry-qualification-detail', {
            parent: 'entity',
            url: '/entry-qualification/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'EntryQualification'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entry-qualification/entry-qualification-detail.html',
                    controller: 'EntryQualificationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EntryQualification', function($stateParams, EntryQualification) {
                    return EntryQualification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entry-qualification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entry-qualification-detail.edit', {
            parent: 'entry-qualification-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entry-qualification/entry-qualification-dialog.html',
                    controller: 'EntryQualificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EntryQualification', function(EntryQualification) {
                            return EntryQualification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entry-qualification.new', {
            parent: 'entry-qualification',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entry-qualification/entry-qualification-dialog.html',
                    controller: 'EntryQualificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                content: null,
                                displayOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entry-qualification', null, { reload: 'entry-qualification' });
                }, function() {
                    $state.go('entry-qualification');
                });
            }]
        })
        .state('entry-qualification.edit', {
            parent: 'entry-qualification',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entry-qualification/entry-qualification-dialog.html',
                    controller: 'EntryQualificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EntryQualification', function(EntryQualification) {
                            return EntryQualification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entry-qualification', null, { reload: 'entry-qualification' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entry-qualification.delete', {
            parent: 'entry-qualification',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entry-qualification/entry-qualification-delete-dialog.html',
                    controller: 'EntryQualificationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EntryQualification', function(EntryQualification) {
                            return EntryQualification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entry-qualification', null, { reload: 'entry-qualification' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
