(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('forum-room', {
            parent: 'entity',
            url: '/forum-room?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ForumRooms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forum-room/forum-rooms.html',
                    controller: 'ForumRoomController',
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
        .state('forum-room-detail', {
            parent: 'entity',
            url: '/forum-room/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ForumRoom'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/forum-room/forum-room-detail.html',
                    controller: 'ForumRoomDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ForumRoom', function($stateParams, ForumRoom) {
                    return ForumRoom.get({id : $stateParams.id}).$promise;
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
        .state('forum-room-detail.edit', {
            parent: 'forum-room-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room/forum-room-dialog.html',
                    controller: 'ForumRoomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ForumRoom', function(ForumRoom) {
                            return ForumRoom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forum-room.new', {
            parent: 'forum-room',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room/forum-room-dialog.html',
                    controller: 'ForumRoomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                forumRoomName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('forum-room', null, { reload: 'forum-room' });
                }, function() {
                    $state.go('forum-room');
                });
            }]
        })
        .state('forum-room.edit', {
            parent: 'forum-room',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room/forum-room-dialog.html',
                    controller: 'ForumRoomDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ForumRoom', function(ForumRoom) {
                            return ForumRoom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forum-room', null, { reload: 'forum-room' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('forum-room.delete', {
            parent: 'forum-room',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/forum-room/forum-room-delete-dialog.html',
                    controller: 'ForumRoomDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ForumRoom', function(ForumRoom) {
                            return ForumRoom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('forum-room', null, { reload: 'forum-room' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
