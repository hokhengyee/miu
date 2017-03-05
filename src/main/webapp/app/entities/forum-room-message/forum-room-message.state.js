(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('forum-room-message', {
            parent: 'entity',
            url: '/forum-room-message?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'ForumRoomMessages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forum-room-message/forum-room-messages.html',
                    controller: 'ForumRoomMessageController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('forum-room-message-detail', {
            parent: 'entity',
            url: '/forum-room-message/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'ForumRoomMessage'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forum-room-message/forum-room-message-detail.html',
                    controller: 'ForumRoomMessageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ForumRoomMessage', function($stateParams, ForumRoomMessage) {
                    return ForumRoomMessage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'forum-room-message',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('forum-room-message-detail.edit', {
            parent: 'forum-room-message-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room-message/forum-room-message-dialog.html',
                    controller: 'ForumRoomMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ForumRoomMessage', function(ForumRoomMessage) {
                            return ForumRoomMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forum-room-message.new', {
            parent: 'forum-room-message',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room-message/forum-room-message-dialog.html',
                    controller: 'ForumRoomMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                message: null,
                                messageDatetime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('forum-room-message', null, { reload: 'forum-room-message' });
                }, function() {
                    $state.go('forum-room-message');
                });
            }]
        })
        .state('forum-room-message.edit', {
            parent: 'forum-room-message',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room-message/forum-room-message-dialog.html',
                    controller: 'ForumRoomMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ForumRoomMessage', function(ForumRoomMessage) {
                            return ForumRoomMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forum-room-message', null, { reload: 'forum-room-message' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forum-room-message.delete', {
            parent: 'forum-room-message',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room-message/forum-room-message-delete-dialog.html',
                    controller: 'ForumRoomMessageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ForumRoomMessage', function(ForumRoomMessage) {
                            return ForumRoomMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forum-room-message', null, { reload: 'forum-room-message' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
