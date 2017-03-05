(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('adjunct-faculty', {
            parent: 'entity',
            url: '/adjunct-faculty?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'AdjunctFaculties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/adjunct-faculty/adjunct-faculties.html',
                    controller: 'AdjunctFacultyController',
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
        .state('adjunct-faculty-detail', {
            parent: 'entity',
            url: '/adjunct-faculty/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'AdjunctFaculty'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/adjunct-faculty/adjunct-faculty-detail.html',
                    controller: 'AdjunctFacultyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AdjunctFaculty', function($stateParams, AdjunctFaculty) {
                    return AdjunctFaculty.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'adjunct-faculty',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('adjunct-faculty-detail.edit', {
            parent: 'adjunct-faculty-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adjunct-faculty/adjunct-faculty-dialog.html',
                    controller: 'AdjunctFacultyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdjunctFaculty', function(AdjunctFaculty) {
                            return AdjunctFaculty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('adjunct-faculty.new', {
            parent: 'adjunct-faculty',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adjunct-faculty/adjunct-faculty-dialog.html',
                    controller: 'AdjunctFacultyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                showOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('adjunct-faculty', null, { reload: 'adjunct-faculty' });
                }, function() {
                    $state.go('adjunct-faculty');
                });
            }]
        })
        .state('adjunct-faculty.edit', {
            parent: 'adjunct-faculty',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adjunct-faculty/adjunct-faculty-dialog.html',
                    controller: 'AdjunctFacultyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdjunctFaculty', function(AdjunctFaculty) {
                            return AdjunctFaculty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('adjunct-faculty', null, { reload: 'adjunct-faculty' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('adjunct-faculty.delete', {
            parent: 'adjunct-faculty',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adjunct-faculty/adjunct-faculty-delete-dialog.html',
                    controller: 'AdjunctFacultyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AdjunctFaculty', function(AdjunctFaculty) {
                            return AdjunctFaculty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('adjunct-faculty', null, { reload: 'adjunct-faculty' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
