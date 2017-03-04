(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('public-online-application', {
            parent: 'study',
            url: '/public-online-application',
            data: {
                authorities: [],
                pageTitle: 'Online Application'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/apply-course/public-online-application.html',
                    controller: 'ApplyCourseController',
                    controllerAs: 'vm'
                }
            },
            resolve : {
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
        })        
        .state('public-online-application-success', {
            parent: 'study',
            url: '/public-online-application-success',
            data: {
                authorities: [],
                pageTitle: 'Online Application Submitted'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/apply-course/apply-course-complete.html',
                    controller: 'ApplyCourseCompleteController',
                    controllerAs: 'vm'
                }
            }
        });
    }

})();

