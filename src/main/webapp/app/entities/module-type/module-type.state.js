(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('module-type', {
            parent: 'entity',
            url: '/module-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ModuleTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/module-type/module-types.html',
                    controller: 'ModuleTypeController',
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
        .state('module-type-detail', {
            parent: 'entity',
            url: '/module-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ModuleType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/module-type/module-type-detail.html',
                    controller: 'ModuleTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ModuleType', function($stateParams, ModuleType) {
                    return ModuleType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'module-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('module-type-detail.edit', {
            parent: 'module-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-type/module-type-dialog.html',
                    controller: 'ModuleTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModuleType', function(ModuleType) {
                            return ModuleType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('module-type.new', {
            parent: 'module-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-type/module-type-dialog.html',
                    controller: 'ModuleTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                moduleTypeOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('module-type', null, { reload: 'module-type' });
                }, function() {
                    $state.go('module-type');
                });
            }]
        })
        .state('module-type.edit', {
            parent: 'module-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-type/module-type-dialog.html',
                    controller: 'ModuleTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModuleType', function(ModuleType) {
                            return ModuleType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('module-type', null, { reload: 'module-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('module-type.delete', {
            parent: 'module-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-type/module-type-delete-dialog.html',
                    controller: 'ModuleTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ModuleType', function(ModuleType) {
                            return ModuleType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('module-type', null, { reload: 'module-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
