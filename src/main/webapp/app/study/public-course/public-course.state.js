(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('public-course', {
            parent: 'study',
            url: '/courses',
            data: {
                authorities: [],
                pageTitle: 'Courses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/public-course/public-courses.html',
                    controller: 'PublicCourseController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
