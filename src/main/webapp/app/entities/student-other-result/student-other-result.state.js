(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-other-result', {
            parent: 'entity',
            url: '/student-other-result?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StudentOtherResults'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-other-result/student-other-results.html',
                    controller: 'StudentOtherResultController',
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
        .state('student-other-result-detail', {
            parent: 'entity',
            url: '/student-other-result/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'StudentOtherResult'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-other-result/student-other-result-detail.html',
                    controller: 'StudentOtherResultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StudentOtherResult', function($stateParams, StudentOtherResult) {
                    return StudentOtherResult.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-other-result',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-other-result-detail.edit', {
            parent: 'student-other-result-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-other-result/student-other-result-dialog.html',
                    controller: 'StudentOtherResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentOtherResult', function(StudentOtherResult) {
                            return StudentOtherResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-other-result.new', {
            parent: 'student-other-result',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-other-result/student-other-result-dialog.html',
                    controller: 'StudentOtherResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                title: null,
                                result: null,
                                dateGraded: null,
                                resultOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-other-result', null, { reload: 'student-other-result' });
                }, function() {
                    $state.go('student-other-result');
                });
            }]
        })
        .state('student-other-result.edit', {
            parent: 'student-other-result',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-other-result/student-other-result-dialog.html',
                    controller: 'StudentOtherResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentOtherResult', function(StudentOtherResult) {
                            return StudentOtherResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-other-result', null, { reload: 'student-other-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-other-result.delete', {
            parent: 'student-other-result',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-other-result/student-other-result-delete-dialog.html',
                    controller: 'StudentOtherResultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentOtherResult', function(StudentOtherResult) {
                            return StudentOtherResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-other-result', null, { reload: 'student-other-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
