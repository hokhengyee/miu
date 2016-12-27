(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('public-entry-qualification', {
            parent: 'study',
            url: '/study/entry-qualification',
            data: {
                authorities: [],
                pageTitle: 'Entry Qualification'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/public-entry-qualification/public-entry-qualification.html',
                    controller: 'PublicEntryQualificationController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
