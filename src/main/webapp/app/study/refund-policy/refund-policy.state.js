(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('refund-policy', {
            parent: 'study',
            url: '/refund-policy',
            data: {
                authorities: [],
                pageTitle: 'Refund Policy'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/refund-policy/refund-policy.html',
                    controller: 'RefundPolicyController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
