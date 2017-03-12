(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('course-module', {
            parent: 'entity',
            url: '/course-module?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseModules'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-module/course-modules.html',
                    controller: 'CourseModuleController',
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
        .state('course-module-detail', {
            parent: 'entity',
            url: '/course-module/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseModule'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-module/course-module-detail.html',
                    controller: 'CourseModuleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CourseModule', function($stateParams, CourseModule) {
                    return CourseModule.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'course-module',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('course-module-detail.edit', {
            parent: 'course-module-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-module/course-module-dialog.html',
                    controller: 'CourseModuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseModule', function(CourseModule) {
                            return CourseModule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-module.new', {
            parent: 'course-module',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-module/course-module-dialog.html',
                    controller: 'CourseModuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                displayOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('course-module', null, { reload: 'course-module' });
                }, function() {
                    $state.go('course-module');
                });
            }]
        })
        .state('course-module.edit', {
            parent: 'course-module',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-module/course-module-dialog.html',
                    controller: 'CourseModuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseModule', function(CourseModule) {
                            return CourseModule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-module', null, { reload: 'course-module' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-module.delete', {
            parent: 'course-module',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-module/course-module-delete-dialog.html',
                    controller: 'CourseModuleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CourseModule', function(CourseModule) {
                            return CourseModule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-module', null, { reload: 'course-module' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
