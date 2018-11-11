(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('static-page', {
            parent: 'entity',
            url: '/static-page?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StaticPages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/static-page/static-pages.html',
                    controller: 'StaticPageController',
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
        .state('static-page-detail', {
            parent: 'entity',
            url: '/static-page/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StaticPage'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/static-page/static-page-detail.html',
                    controller: 'StaticPageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StaticPage', function($stateParams, StaticPage) {
                    return StaticPage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'static-page',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('static-page-detail.edit', {
            parent: 'static-page-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page/static-page-dialog.html',
                    controller: 'StaticPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StaticPage', function(StaticPage) {
                            return StaticPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('static-page.new', {
            parent: 'static-page',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page/static-page-dialog.html',
                    controller: 'StaticPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                content: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('static-page', null, { reload: 'static-page' });
                }, function() {
                    $state.go('static-page');
                });
            }]
        })
        .state('static-page.edit', {
            parent: 'static-page',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page/static-page-dialog.html',
                    controller: 'StaticPageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StaticPage', function(StaticPage) {
                            return StaticPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('static-page', null, { reload: 'static-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('static-page.delete', {
            parent: 'static-page',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/static-page/static-page-delete-dialog.html',
                    controller: 'StaticPageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StaticPage', function(StaticPage) {
                            return StaticPage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('static-page', null, { reload: 'static-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
