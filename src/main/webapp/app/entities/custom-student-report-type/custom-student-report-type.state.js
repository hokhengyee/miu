(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('custom-student-report-type', {
            parent: 'entity',
            url: '/custom-student-report-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CustomStudentReportTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/custom-student-report-type/custom-student-report-types.html',
                    controller: 'CustomStudentReportTypeController',
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
        .state('custom-student-report-type-detail', {
            parent: 'entity',
            url: '/custom-student-report-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CustomStudentReportType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/custom-student-report-type/custom-student-report-type-detail.html',
                    controller: 'CustomStudentReportTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CustomStudentReportType', function($stateParams, CustomStudentReportType) {
                    return CustomStudentReportType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'custom-student-report-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('custom-student-report-type-detail.edit', {
            parent: 'custom-student-report-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/custom-student-report-type/custom-student-report-type-dialog.html',
                    controller: 'CustomStudentReportTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomStudentReportType', function(CustomStudentReportType) {
                            return CustomStudentReportType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('custom-student-report-type.new', {
            parent: 'custom-student-report-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/custom-student-report-type/custom-student-report-type-dialog.html',
                    controller: 'CustomStudentReportTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                label: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('custom-student-report-type', null, { reload: 'custom-student-report-type' });
                }, function() {
                    $state.go('custom-student-report-type');
                });
            }]
        })
        .state('custom-student-report-type.edit', {
            parent: 'custom-student-report-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/custom-student-report-type/custom-student-report-type-dialog.html',
                    controller: 'CustomStudentReportTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomStudentReportType', function(CustomStudentReportType) {
                            return CustomStudentReportType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('custom-student-report-type', null, { reload: 'custom-student-report-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('custom-student-report-type.delete', {
            parent: 'custom-student-report-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/custom-student-report-type/custom-student-report-type-delete-dialog.html',
                    controller: 'CustomStudentReportTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomStudentReportType', function(CustomStudentReportType) {
                            return CustomStudentReportType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('custom-student-report-type', null, { reload: 'custom-student-report-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
