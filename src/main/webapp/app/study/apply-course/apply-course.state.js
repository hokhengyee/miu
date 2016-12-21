(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('apply-course', {
            parent: 'study',
            url: '/online-application',
            data: {
                authorities: [],
                pageTitle: 'Online Application'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/apply-course/apply-course.html',
                    controller: 'ApplyCourseController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
