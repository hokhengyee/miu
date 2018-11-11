(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('salutation', {
            parent: 'entity',
            url: '/salutation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Salutations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salutation/salutations.html',
                    controller: 'SalutationController',
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
        .state('salutation-detail', {
            parent: 'entity',
            url: '/salutation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Salutation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salutation/salutation-detail.html',
                    controller: 'SalutationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Salutation', function($stateParams, Salutation) {
                    return Salutation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'salutation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('salutation-detail.edit', {
            parent: 'salutation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salutation/salutation-dialog.html',
                    controller: 'SalutationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Salutation', function(Salutation) {
                            return Salutation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salutation.new', {
            parent: 'salutation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salutation/salutation-dialog.html',
                    controller: 'SalutationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                displayOrder: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('salutation', null, { reload: 'salutation' });
                }, function() {
                    $state.go('salutation');
                });
            }]
        })
        .state('salutation.edit', {
            parent: 'salutation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salutation/salutation-dialog.html',
                    controller: 'SalutationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Salutation', function(Salutation) {
                            return Salutation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salutation', null, { reload: 'salutation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salutation.delete', {
            parent: 'salutation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salutation/salutation-delete-dialog.html',
                    controller: 'SalutationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Salutation', function(Salutation) {
                            return Salutation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salutation', null, { reload: 'salutation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
