(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('registration-academic-details', {
            parent: 'entity',
            url: '/registration-academic-details?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RegistrationAcademicDetails'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registration-academic-details/registration-academic-details.html',
                    controller: 'RegistrationAcademicDetailsController',
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
        .state('registration-academic-details-detail', {
            parent: 'entity',
            url: '/registration-academic-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RegistrationAcademicDetails'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registration-academic-details/registration-academic-details-detail.html',
                    controller: 'RegistrationAcademicDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RegistrationAcademicDetails', function($stateParams, RegistrationAcademicDetails) {
                    return RegistrationAcademicDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'registration-academic-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('registration-academic-details-detail.edit', {
            parent: 'registration-academic-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-academic-details/registration-academic-details-dialog.html',
                    controller: 'RegistrationAcademicDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegistrationAcademicDetails', function(RegistrationAcademicDetails) {
                            return RegistrationAcademicDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registration-academic-details.new', {
            parent: 'registration-academic-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-academic-details/registration-academic-details-dialog.html',
                    controller: 'RegistrationAcademicDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameOfInstitution2: null,
                                examPassed2: null,
                                year2: null,
                                grade2: null,
                                nameOfInstitution3: null,
                                examPassed3: null,
                                year3: null,
                                grade3: null,
                                nameOfInstitution4: null,
                                examPassed4: null,
                                year4: null,
                                grade4: null,
                                md5key: null,
                                nameOfInstitution1: null,
                                examPassed1: null,
                                year1: null,
                                grade1: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('registration-academic-details', null, { reload: 'registration-academic-details' });
                }, function() {
                    $state.go('registration-academic-details');
                });
            }]
        })
        .state('registration-academic-details.edit', {
            parent: 'registration-academic-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-academic-details/registration-academic-details-dialog.html',
                    controller: 'RegistrationAcademicDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegistrationAcademicDetails', function(RegistrationAcademicDetails) {
                            return RegistrationAcademicDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registration-academic-details', null, { reload: 'registration-academic-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registration-academic-details.delete', {
            parent: 'registration-academic-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registration-academic-details/registration-academic-details-delete-dialog.html',
                    controller: 'RegistrationAcademicDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RegistrationAcademicDetails', function(RegistrationAcademicDetails) {
                            return RegistrationAcademicDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registration-academic-details', null, { reload: 'registration-academic-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
