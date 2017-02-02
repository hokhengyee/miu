(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-payment', {
            parent: 'entity',
            url: '/student-payment?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'StudentPayments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-payment/student-payments.html',
                    controller: 'StudentPaymentController',
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
        .state('student-payment-detail', {
            parent: 'entity',
            url: '/student-payment/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'StudentPayment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-payment/student-payment-detail.html',
                    controller: 'StudentPaymentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'StudentPayment', function($stateParams, StudentPayment) {
                    return StudentPayment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-payment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-payment-detail.edit', {
            parent: 'student-payment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-payment/student-payment-dialog.html',
                    controller: 'StudentPaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentPayment', function(StudentPayment) {
                            return StudentPayment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-payment.new', {
            parent: 'student-payment',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-payment/student-payment-dialog.html',
                    controller: 'StudentPaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdDate: null,
                                amount: null,
                                description: null,
                                paymentDate: null,
                                paid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-payment', null, { reload: 'student-payment' });
                }, function() {
                    $state.go('student-payment');
                });
            }]
        })
        .state('student-payment.edit', {
            parent: 'student-payment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-payment/student-payment-dialog.html',
                    controller: 'StudentPaymentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentPayment', function(StudentPayment) {
                            return StudentPayment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-payment', null, { reload: 'student-payment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-payment.delete', {
            parent: 'student-payment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-payment/student-payment-delete-dialog.html',
                    controller: 'StudentPaymentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentPayment', function(StudentPayment) {
                            return StudentPayment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-payment', null, { reload: 'student-payment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
