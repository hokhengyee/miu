(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('faculty-alumni', {
            parent: 'study',
            url: '/faculty-and-alumni',
            data: {
                authorities: [],
                pageTitle: 'Faculty & Alumni'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/faculty-alumni/faculty-alumni.html',
                    controller: 'FacultyAlumniController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
