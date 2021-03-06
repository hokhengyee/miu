(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-forum-message', {
            parent: 'account',
            url: '/forum/message/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Forum Message'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/my-forum-message/my-forum-message.html',
                    controller: 'MyForumMessageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyForumMsg', function($stateParams, MyForumMsg) {
                    return MyForumMsg.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'forum-room',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-forum-message.delete', {
            parent: 'account',
            url: '/forum/{forumid}/message/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/account/my-forum-message/my-forum-message-delete-dialog.html',
                    controller: 'MyForumMessageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyForumMsg', function(MyForumMsg) {
                            return MyForumMsg.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-forum-room', {id: $stateParams.forumid }, { reload: 'my-forum-room' });
                }, function() {
                    $state.go('my-forum-room', {id: $stateParams.forumid });
                });
            }]
        });
    }

})();
