(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('academic-certificate', {
            parent: 'entity',
            url: '/academic-certificate?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'AcademicCertificates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/academic-certificate/academic-certificates.html',
                    controller: 'AcademicCertificateController',
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
        .state('academic-certificate-detail', {
            parent: 'entity',
            url: '/academic-certificate/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'AcademicCertificate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/academic-certificate/academic-certificate-detail.html',
                    controller: 'AcademicCertificateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AcademicCertificate', function($stateParams, AcademicCertificate) {
                    return AcademicCertificate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'academic-certificate',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('academic-certificate-detail.edit', {
            parent: 'academic-certificate-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-certificate/academic-certificate-dialog.html',
                    controller: 'AcademicCertificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AcademicCertificate', function(AcademicCertificate) {
                            return AcademicCertificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('academic-certificate.new', {
            parent: 'academic-certificate',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-certificate/academic-certificate-dialog.html',
                    controller: 'AcademicCertificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                md5Key: null,
                                academicCertificate1: null,
                                academicCertificate1ContentType: null,
                                academicCertificate2: null,
                                academicCertificate2ContentType: null,
                                academicCertificate3: null,
                                academicCertificate3ContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('academic-certificate', null, { reload: 'academic-certificate' });
                }, function() {
                    $state.go('academic-certificate');
                });
            }]
        })
        .state('academic-certificate.edit', {
            parent: 'academic-certificate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-certificate/academic-certificate-dialog.html',
                    controller: 'AcademicCertificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AcademicCertificate', function(AcademicCertificate) {
                            return AcademicCertificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('academic-certificate', null, { reload: 'academic-certificate' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('academic-certificate.delete', {
            parent: 'academic-certificate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/academic-certificate/academic-certificate-delete-dialog.html',
                    controller: 'AcademicCertificateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AcademicCertificate', function(AcademicCertificate) {
                            return AcademicCertificate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('academic-certificate', null, { reload: 'academic-certificate' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
