(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('apply-course-complete', {
            parent: 'study',
            url: '/online-registration-details',
            data: {
                authorities: [],
                pageTitle: 'OnlineApplication'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/apply-course/apply-course-complete.html',
                    controller: 'ApplyCourseCompleteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'public-online-application',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    
                    return currentStateData;
                }]
            }
        })
        .state('apply-course', {
            parent: 'study',
            url: '/online-registration',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/study/apply-course/apply-course-dialog.html',
                    controller: 'ApplyCourseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateOfBirth: null,
                                telephone: null,
                                email: null,
                                city: null,
                                state: null,
                                country: null,
                                postcode: null,
                                registrationDatetime: null,
                                surname: null,
                                givenName: null,
                                address: null,
                                applicationForm: null,
                                applicationFormContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('apply-course-complete', null, { reload: 'apply-course-complete' });
                }, function() {
                    $state.go('apply-course-complete');
                });
            }]
        });
    }

})();

