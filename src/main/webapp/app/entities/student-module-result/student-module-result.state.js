(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-module-result', {
            parent: 'entity',
            url: '/student-module-result?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StudentModuleResults'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-module-result/student-module-results.html',
                    controller: 'StudentModuleResultController',
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
        .state('student-module-result-detail', {
            parent: 'entity',
            url: '/student-module-result/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StudentModuleResult'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-module-result/student-module-result-detail.html',
                    controller: 'StudentModuleResultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StudentModuleResult', function($stateParams, StudentModuleResult) {
                    return StudentModuleResult.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-module-result',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-module-result-detail.edit', {
            parent: 'student-module-result-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-module-result/student-module-result-dialog.html',
                    controller: 'StudentModuleResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentModuleResult', function(StudentModuleResult) {
                            return StudentModuleResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-module-result.new', {
            parent: 'student-module-result',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-module-result/student-module-result-dialog.html',
                    controller: 'StudentModuleResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                result: null,
                                dateGraded: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-module-result', null, { reload: 'student-module-result' });
                }, function() {
                    $state.go('student-module-result');
                });
            }]
        })
        .state('student-module-result.edit', {
            parent: 'student-module-result',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-module-result/student-module-result-dialog.html',
                    controller: 'StudentModuleResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentModuleResult', function(StudentModuleResult) {
                            return StudentModuleResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-module-result', null, { reload: 'student-module-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-module-result.delete', {
            parent: 'student-module-result',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-module-result/student-module-result-delete-dialog.html',
                    controller: 'StudentModuleResultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentModuleResult', function(StudentModuleResult) {
                            return StudentModuleResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-module-result', null, { reload: 'student-module-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
