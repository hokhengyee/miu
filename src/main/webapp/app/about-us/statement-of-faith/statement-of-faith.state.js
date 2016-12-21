(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('statement-of-faith', {
            parent: 'about-us',
            url: '/statement-of-faith',
            data: {
                authorities: [],
                pageTitle: 'Statement Of Faith'
            },
            views: {
                'content@': {
                    templateUrl: 'app/about-us/statement-of-faith/statement-of-faith.html',
                    controller: 'StatementOfFaithController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
