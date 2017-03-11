(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ministerial-work-experience', {
            parent: 'entity',
            url: '/ministerial-work-experience?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'MinisterialWorkExperiences'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ministerial-work-experience/ministerial-work-experiences.html',
                    controller: 'MinisterialWorkExperienceController',
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
        .state('ministerial-work-experience-detail', {
            parent: 'entity',
            url: '/ministerial-work-experience/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'MinisterialWorkExperience'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ministerial-work-experience/ministerial-work-experience-detail.html',
                    controller: 'MinisterialWorkExperienceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MinisterialWorkExperience', function($stateParams, MinisterialWorkExperience) {
                    return MinisterialWorkExperience.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ministerial-work-experience',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ministerial-work-experience-detail.edit', {
            parent: 'ministerial-work-experience-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ministerial-work-experience/ministerial-work-experience-dialog.html',
                    controller: 'MinisterialWorkExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MinisterialWorkExperience', function(MinisterialWorkExperience) {
                            return MinisterialWorkExperience.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ministerial-work-experience.new', {
            parent: 'ministerial-work-experience',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ministerial-work-experience/ministerial-work-experience-dialog.html',
                    controller: 'MinisterialWorkExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameOfMinistry2: null,
                                areaOfMinistry2: null,
                                nameOfMinistry3: null,
                                areaOfMinistry3: null,
                                nameOfMinistry4: null,
                                areaOfMinistry4: null,
                                md5Key: null,
                                years2: null,
                                years3: null,
                                years4: null,
                                nameOfMinistry1: null,
                                areaOfMinistry1: null,
                                years1: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ministerial-work-experience', null, { reload: 'ministerial-work-experience' });
                }, function() {
                    $state.go('ministerial-work-experience');
                });
            }]
        })
        .state('ministerial-work-experience.edit', {
            parent: 'ministerial-work-experience',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ministerial-work-experience/ministerial-work-experience-dialog.html',
                    controller: 'MinisterialWorkExperienceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MinisterialWorkExperience', function(MinisterialWorkExperience) {
                            return MinisterialWorkExperience.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ministerial-work-experience', null, { reload: 'ministerial-work-experience' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ministerial-work-experience.delete', {
            parent: 'ministerial-work-experience',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ministerial-work-experience/ministerial-work-experience-delete-dialog.html',
                    controller: 'MinisterialWorkExperienceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MinisterialWorkExperience', function(MinisterialWorkExperience) {
                            return MinisterialWorkExperience.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ministerial-work-experience', null, { reload: 'ministerial-work-experience' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
