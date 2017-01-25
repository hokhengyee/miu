(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('my-profile', {
            parent: 'account',
            url: '/my-profile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'miu'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/my-profile/my-profile.html',
                    controller: 'MyProfileController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
