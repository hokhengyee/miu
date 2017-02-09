(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('course-material', {
            parent: 'entity',
            url: '/course-material?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseMaterials'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-material/course-materials.html',
                    controller: 'CourseMaterialController',
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
        .state('course-material-detail', {
            parent: 'entity',
            url: '/course-material/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CourseMaterial'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/course-material/course-material-detail.html',
                    controller: 'CourseMaterialDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CourseMaterial', function($stateParams, CourseMaterial) {
                    return CourseMaterial.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'course-material',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('course-material-detail.edit', {
            parent: 'course-material-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material/course-material-dialog.html',
                    controller: 'CourseMaterialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseMaterial', function(CourseMaterial) {
                            return CourseMaterial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-material.new', {
            parent: 'course-material',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material/course-material-dialog.html',
                    controller: 'CourseMaterialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                websiteLink: null,
                                content: null,
                                contentContentType: null,
                                displayOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('course-material', null, { reload: 'course-material' });
                }, function() {
                    $state.go('course-material');
                });
            }]
        })
        .state('course-material.edit', {
            parent: 'course-material',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material/course-material-dialog.html',
                    controller: 'CourseMaterialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CourseMaterial', function(CourseMaterial) {
                            return CourseMaterial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-material', null, { reload: 'course-material' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('course-material.delete', {
            parent: 'course-material',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/course-material/course-material-delete-dialog.html',
                    controller: 'CourseMaterialDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CourseMaterial', function(CourseMaterial) {
                            return CourseMaterial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('course-material', null, { reload: 'course-material' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
