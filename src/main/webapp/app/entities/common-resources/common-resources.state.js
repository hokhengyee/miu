(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('common-resources', {
            parent: 'entity',
            url: '/common-resources?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommonResources'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/common-resources/common-resources.html',
                    controller: 'CommonResourcesController',
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
        .state('common-resources-detail', {
            parent: 'entity',
            url: '/common-resources/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommonResources'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/common-resources/common-resources-detail.html',
                    controller: 'CommonResourcesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CommonResources', function($stateParams, CommonResources) {
                    return CommonResources.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'common-resources',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('common-resources-detail.edit', {
            parent: 'common-resources-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-resources/common-resources-dialog.html',
                    controller: 'CommonResourcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommonResources', function(CommonResources) {
                            return CommonResources.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('common-resources.new', {
            parent: 'common-resources',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-resources/common-resources-dialog.html',
                    controller: 'CommonResourcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                content: null,
                                contentContentType: null,
                                description: null,
                                displayOrder: null,
                                websiteLink: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('common-resources', null, { reload: 'common-resources' });
                }, function() {
                    $state.go('common-resources');
                });
            }]
        })
        .state('common-resources.edit', {
            parent: 'common-resources',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-resources/common-resources-dialog.html',
                    controller: 'CommonResourcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommonResources', function(CommonResources) {
                            return CommonResources.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('common-resources', null, { reload: 'common-resources' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('common-resources.delete', {
            parent: 'common-resources',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-resources/common-resources-delete-dialog.html',
                    controller: 'CommonResourcesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CommonResources', function(CommonResources) {
                            return CommonResources.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('common-resources', null, { reload: 'common-resources' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
