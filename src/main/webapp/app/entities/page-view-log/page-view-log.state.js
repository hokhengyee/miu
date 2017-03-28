(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('page-view-log', {
            parent: 'entity',
            url: '/page-view-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PageViewLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/page-view-log/page-view-logs.html',
                    controller: 'PageViewLogController',
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
        .state('page-view-log-detail', {
            parent: 'entity',
            url: '/page-view-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PageViewLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/page-view-log/page-view-log-detail.html',
                    controller: 'PageViewLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PageViewLog', function($stateParams, PageViewLog) {
                    return PageViewLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'page-view-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('page-view-log-detail.edit', {
            parent: 'page-view-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/page-view-log/page-view-log-dialog.html',
                    controller: 'PageViewLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PageViewLog', function(PageViewLog) {
                            return PageViewLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('page-view-log.new', {
            parent: 'page-view-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/page-view-log/page-view-log-dialog.html',
                    controller: 'PageViewLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdDate: null,
                                views: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('page-view-log', null, { reload: 'page-view-log' });
                }, function() {
                    $state.go('page-view-log');
                });
            }]
        })
        .state('page-view-log.edit', {
            parent: 'page-view-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/page-view-log/page-view-log-dialog.html',
                    controller: 'PageViewLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PageViewLog', function(PageViewLog) {
                            return PageViewLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('page-view-log', null, { reload: 'page-view-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('page-view-log.delete', {
            parent: 'page-view-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/page-view-log/page-view-log-delete-dialog.html',
                    controller: 'PageViewLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PageViewLog', function(PageViewLog) {
                            return PageViewLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('page-view-log', null, { reload: 'page-view-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
