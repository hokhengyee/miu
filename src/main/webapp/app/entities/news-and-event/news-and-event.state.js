(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('news-and-event', {
            parent: 'entity',
            url: '/news-and-event?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'NewsAndEvents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/news-and-event/news-and-events.html',
                    controller: 'NewsAndEventController',
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
        .state('news-and-event-detail', {
            parent: 'entity',
            url: '/news-and-event/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'NewsAndEvent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/news-and-event/news-and-event-detail.html',
                    controller: 'NewsAndEventDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'NewsAndEvent', function($stateParams, NewsAndEvent) {
                    return NewsAndEvent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'news-and-event',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('news-and-event-detail.edit', {
            parent: 'news-and-event-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/news-and-event/news-and-event-dialog.html',
                    controller: 'NewsAndEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewsAndEvent', function(NewsAndEvent) {
                            return NewsAndEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('news-and-event.new', {
            parent: 'news-and-event',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/news-and-event/news-and-event-dialog.html',
                    controller: 'NewsAndEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                websiteLink: null,
                                startDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('news-and-event', null, { reload: 'news-and-event' });
                }, function() {
                    $state.go('news-and-event');
                });
            }]
        })
        .state('news-and-event.edit', {
            parent: 'news-and-event',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/news-and-event/news-and-event-dialog.html',
                    controller: 'NewsAndEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NewsAndEvent', function(NewsAndEvent) {
                            return NewsAndEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('news-and-event', null, { reload: 'news-and-event' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('news-and-event.delete', {
            parent: 'news-and-event',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/news-and-event/news-and-event-delete-dialog.html',
                    controller: 'NewsAndEventDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NewsAndEvent', function(NewsAndEvent) {
                            return NewsAndEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('news-and-event', null, { reload: 'news-and-event' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
