(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('accredited-centers', {
            parent: 'about-us',
            url: '/accredited-centers',
            data: {
                authorities: [],
                pageTitle: 'Accredited Centers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/about-us/accredited-centers/accredited-centers.html',
                    controller: 'AccreditedCentersController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
