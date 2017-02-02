(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('research-paper', {
            parent: 'entity',
            url: '/research-paper?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'ResearchPapers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/research-paper/research-papers.html',
                    controller: 'ResearchPaperController',
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
        .state('research-paper-detail', {
            parent: 'entity',
            url: '/research-paper/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'ResearchPaper'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/research-paper/research-paper-detail.html',
                    controller: 'ResearchPaperDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ResearchPaper', function($stateParams, ResearchPaper) {
                    return ResearchPaper.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'research-paper',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('research-paper-detail.edit', {
            parent: 'research-paper-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/research-paper/research-paper-dialog.html',
                    controller: 'ResearchPaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResearchPaper', function(ResearchPaper) {
                            return ResearchPaper.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('research-paper.new', {
            parent: 'research-paper',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/research-paper/research-paper-dialog.html',
                    controller: 'ResearchPaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                title: null,
                                showOrder: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('research-paper', null, { reload: 'research-paper' });
                }, function() {
                    $state.go('research-paper');
                });
            }]
        })
        .state('research-paper.edit', {
            parent: 'research-paper',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/research-paper/research-paper-dialog.html',
                    controller: 'ResearchPaperDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResearchPaper', function(ResearchPaper) {
                            return ResearchPaper.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('research-paper', null, { reload: 'research-paper' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('research-paper.delete', {
            parent: 'research-paper',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/research-paper/research-paper-delete-dialog.html',
                    controller: 'ResearchPaperDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResearchPaper', function(ResearchPaper) {
                            return ResearchPaper.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('research-paper', null, { reload: 'research-paper' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
