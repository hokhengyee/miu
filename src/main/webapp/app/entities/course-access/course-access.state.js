(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('course-access', {
            parent: 'entity',
            url: '/course-access?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseAccesses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-access/course-accesses.html',
                    controller: 'CourseAccessController',
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
        .state('course-access-detail', {
            parent: 'entity',
            url: '/course-access/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseAccess'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-access/course-access-detail.html',
                    controller: 'CourseAccessDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CourseAccess', function($stateParams, CourseAccess) {
                    return CourseAccess.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'course-access',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('course-access-detail.edit', {
            parent: 'course-access-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-access/course-access-dialog.html',
                    controller: 'CourseAccessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseAccess', function(CourseAccess) {
                            return CourseAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-access.new', {
            parent: 'course-access',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-access/course-access-dialog.html',
                    controller: 'CourseAccessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('course-access', null, { reload: 'course-access' });
                }, function() {
                    $state.go('course-access');
                });
            }]
        })
        .state('course-access.edit', {
            parent: 'course-access',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-access/course-access-dialog.html',
                    controller: 'CourseAccessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseAccess', function(CourseAccess) {
                            return CourseAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-access', null, { reload: 'course-access' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-access.delete', {
            parent: 'course-access',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-access/course-access-delete-dialog.html',
                    controller: 'CourseAccessDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CourseAccess', function(CourseAccess) {
                            return CourseAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-access', null, { reload: 'course-access' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
