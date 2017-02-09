(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('external-online-resource', {
            parent: 'entity',
            url: '/external-online-resource?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'ExternalOnlineResources'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-online-resource/external-online-resources.html',
                    controller: 'ExternalOnlineResourceController',
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
        .state('external-online-resource-detail', {
            parent: 'entity',
            url: '/external-online-resource/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'ExternalOnlineResource'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-online-resource/external-online-resource-detail.html',
                    controller: 'ExternalOnlineResourceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ExternalOnlineResource', function($stateParams, ExternalOnlineResource) {
                    return ExternalOnlineResource.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'external-online-resource',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('external-online-resource-detail.edit', {
            parent: 'external-online-resource-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-online-resource/external-online-resource-dialog.html',
                    controller: 'ExternalOnlineResourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExternalOnlineResource', function(ExternalOnlineResource) {
                            return ExternalOnlineResource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('external-online-resource.new', {
            parent: 'external-online-resource',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-online-resource/external-online-resource-dialog.html',
                    controller: 'ExternalOnlineResourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                websiteLink: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('external-online-resource', null, { reload: 'external-online-resource' });
                }, function() {
                    $state.go('external-online-resource');
                });
            }]
        })
        .state('external-online-resource.edit', {
            parent: 'external-online-resource',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-online-resource/external-online-resource-dialog.html',
                    controller: 'ExternalOnlineResourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExternalOnlineResource', function(ExternalOnlineResource) {
                            return ExternalOnlineResource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('external-online-resource', null, { reload: 'external-online-resource' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('external-online-resource.delete', {
            parent: 'external-online-resource',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-online-resource/external-online-resource-delete-dialog.html',
                    controller: 'ExternalOnlineResourceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExternalOnlineResource', function(ExternalOnlineResource) {
                            return ExternalOnlineResource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('external-online-resource', null, { reload: 'external-online-resource' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
