(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('my-dashboard', {
            parent: 'account',
            url: '/my-dashboard',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'miu'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/my-dashboard/my-dashboard.html',
                    controller: 'MyDashboardController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
