(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-research-paper-result', {
            parent: 'entity',
            url: '/student-research-paper-result?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StudentResearchPaperResults'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-research-paper-result/student-research-paper-results.html',
                    controller: 'StudentResearchPaperResultController',
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
        .state('student-research-paper-result-detail', {
            parent: 'entity',
            url: '/student-research-paper-result/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StudentResearchPaperResult'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-research-paper-result/student-research-paper-result-detail.html',
                    controller: 'StudentResearchPaperResultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StudentResearchPaperResult', function($stateParams, StudentResearchPaperResult) {
                    return StudentResearchPaperResult.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-research-paper-result',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-research-paper-result-detail.edit', {
            parent: 'student-research-paper-result-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-research-paper-result/student-research-paper-result-dialog.html',
                    controller: 'StudentResearchPaperResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentResearchPaperResult', function(StudentResearchPaperResult) {
                            return StudentResearchPaperResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-research-paper-result.new', {
            parent: 'student-research-paper-result',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-research-paper-result/student-research-paper-result-dialog.html',
                    controller: 'StudentResearchPaperResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                result: null,
                                dateGraded: null,
                                resultOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-research-paper-result', null, { reload: 'student-research-paper-result' });
                }, function() {
                    $state.go('student-research-paper-result');
                });
            }]
        })
        .state('student-research-paper-result.edit', {
            parent: 'student-research-paper-result',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-research-paper-result/student-research-paper-result-dialog.html',
                    controller: 'StudentResearchPaperResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentResearchPaperResult', function(StudentResearchPaperResult) {
                            return StudentResearchPaperResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-research-paper-result', null, { reload: 'student-research-paper-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-research-paper-result.delete', {
            parent: 'student-research-paper-result',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-research-paper-result/student-research-paper-result-delete-dialog.html',
                    controller: 'StudentResearchPaperResultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentResearchPaperResult', function(StudentResearchPaperResult) {
                            return StudentResearchPaperResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-research-paper-result', null, { reload: 'student-research-paper-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
