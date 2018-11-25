(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('record-of-certificate', {
            parent: 'entity',
            url: '/record-of-certificate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RecordOfCertificates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/record-of-certificate/record-of-certificates.html',
                    controller: 'RecordOfCertificateController',
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
        .state('record-of-certificate-detail', {
            parent: 'entity',
            url: '/record-of-certificate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RecordOfCertificate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/record-of-certificate/record-of-certificate-detail.html',
                    controller: 'RecordOfCertificateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RecordOfCertificate', function($stateParams, RecordOfCertificate) {
                    return RecordOfCertificate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'record-of-certificate',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('record-of-certificate-detail.edit', {
            parent: 'record-of-certificate-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record-of-certificate/record-of-certificate-dialog.html',
                    controller: 'RecordOfCertificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RecordOfCertificate', function(RecordOfCertificate) {
                            return RecordOfCertificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('record-of-certificate.new', {
            parent: 'record-of-certificate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record-of-certificate/record-of-certificate-dialog.html',
                    controller: 'RecordOfCertificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                degree: null,
                                studentNo: null,
                                certNumber: null,
                                certScanFile: null,
                                certScanFileContentType: null,
                                certDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('record-of-certificate', null, { reload: 'record-of-certificate' });
                }, function() {
                    $state.go('record-of-certificate');
                });
            }]
        })
        .state('record-of-certificate.edit', {
            parent: 'record-of-certificate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record-of-certificate/record-of-certificate-dialog.html',
                    controller: 'RecordOfCertificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RecordOfCertificate', function(RecordOfCertificate) {
                            return RecordOfCertificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('record-of-certificate', null, { reload: 'record-of-certificate' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('record-of-certificate.delete', {
            parent: 'record-of-certificate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/record-of-certificate/record-of-certificate-delete-dialog.html',
                    controller: 'RecordOfCertificateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RecordOfCertificate', function(RecordOfCertificate) {
                            return RecordOfCertificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('record-of-certificate', null, { reload: 'record-of-certificate' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
