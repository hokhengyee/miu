(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('course-material-access', {
            parent: 'entity',
            url: '/course-material-access?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseMaterialAccesses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-material-access/course-material-accesses.html',
                    controller: 'CourseMaterialAccessController',
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
        .state('course-material-access-detail', {
            parent: 'entity',
            url: '/course-material-access/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseMaterialAccess'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-material-access/course-material-access-detail.html',
                    controller: 'CourseMaterialAccessDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CourseMaterialAccess', function($stateParams, CourseMaterialAccess) {
                    return CourseMaterialAccess.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'course-material-access',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('course-material-access-detail.edit', {
            parent: 'course-material-access-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material-access/course-material-access-dialog.html',
                    controller: 'CourseMaterialAccessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseMaterialAccess', function(CourseMaterialAccess) {
                            return CourseMaterialAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-material-access.new', {
            parent: 'course-material-access',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material-access/course-material-access-dialog.html',
                    controller: 'CourseMaterialAccessDialogController',
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
                    $state.go('course-material-access', null, { reload: 'course-material-access' });
                }, function() {
                    $state.go('course-material-access');
                });
            }]
        })
        .state('course-material-access.edit', {
            parent: 'course-material-access',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material-access/course-material-access-dialog.html',
                    controller: 'CourseMaterialAccessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseMaterialAccess', function(CourseMaterialAccess) {
                            return CourseMaterialAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-material-access', null, { reload: 'course-material-access' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-material-access.delete', {
            parent: 'course-material-access',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material-access/course-material-access-delete-dialog.html',
                    controller: 'CourseMaterialAccessDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CourseMaterialAccess', function(CourseMaterialAccess) {
                            return CourseMaterialAccess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-material-access', null, { reload: 'course-material-access' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
