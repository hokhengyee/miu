(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('static-page-type', {
            parent: 'entity',
            url: '/static-page-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StaticPageTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/static-page-type/static-page-types.html',
                    controller: 'StaticPageTypeController',
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
        .state('static-page-type-detail', {
            parent: 'entity',
            url: '/static-page-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StaticPageType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/static-page-type/static-page-type-detail.html',
                    controller: 'StaticPageTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StaticPageType', function($stateParams, StaticPageType) {
                    return StaticPageType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'static-page-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('static-page-type-detail.edit', {
            parent: 'static-page-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page-type/static-page-type-dialog.html',
                    controller: 'StaticPageTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StaticPageType', function(StaticPageType) {
                            return StaticPageType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('static-page-type.new', {
            parent: 'static-page-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page-type/static-page-type-dialog.html',
                    controller: 'StaticPageTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('static-page-type', null, { reload: 'static-page-type' });
                }, function() {
                    $state.go('static-page-type');
                });
            }]
        })
        .state('static-page-type.edit', {
            parent: 'static-page-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page-type/static-page-type-dialog.html',
                    controller: 'StaticPageTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StaticPageType', function(StaticPageType) {
                            return StaticPageType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('static-page-type', null, { reload: 'static-page-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('static-page-type.delete', {
            parent: 'static-page-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page-type/static-page-type-delete-dialog.html',
                    controller: 'StaticPageTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StaticPageType', function(StaticPageType) {
                            return StaticPageType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('static-page-type', null, { reload: 'static-page-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
