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
                        md5key: null,
                        id: null
                    };
                },
                entity2: function () {
                    return {
                        nameOfInstitution1: null,
                        year1: null,
                        grade1: null,
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
                        examPassed1: null,
                        md5key: null,
                        id: null
                    };
                },
                entity3: function () {
                    return {
                        nameOfMinistry1: null,
                        areaOfMinistry1: null,
                        years1: null,
                        nameOfMinistry2: null,
                        areaOfMinistry2: null,
                        years2: null,
                        nameOfMinistry3: null,
                        areaOfMinistry3: null,
                        years3: null,
                        nameOfMinistry4: null,
                        areaOfMinistry4: null,
                        years4: null,
                        md5Key: null,
                        id: null
                    };
                },
                entity4: function () {
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

