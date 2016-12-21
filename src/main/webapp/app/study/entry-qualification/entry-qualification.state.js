(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('entry-qualification', {
            parent: 'study',
            url: '/entry-qualification',
            data: {
                authorities: [],
                pageTitle: 'Entry Qualification'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/entry-qualification/entry-qualification.html',
                    controller: 'EntryQualificationController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
