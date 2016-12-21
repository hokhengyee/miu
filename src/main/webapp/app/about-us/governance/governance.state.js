(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('governance', {
            parent: 'about-us',
            url: '/governance',
            data: {
                authorities: [],
                pageTitle: 'Governance'
            },
            views: {
                'content@': {
                    templateUrl: 'app/about-us/governance/governance.html',
                    controller: 'GovernanceController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
