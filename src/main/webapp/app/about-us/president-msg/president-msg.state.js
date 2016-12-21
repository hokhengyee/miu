(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('president-msg', {
            parent: 'about-us',
            url: '/message-from-the-president',
            data: {
                authorities: [],
                pageTitle: 'Message from the President'
            },
            views: {
                'content@': {
                    templateUrl: 'app/about-us/president-msg/president-msg.html',
                    controller: 'PresidentMsgController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
