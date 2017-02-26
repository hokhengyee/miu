(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('public-adjunct-faculty', {
            parent: 'study',
            url: '/public-adjunct-faculty?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'Adjunct Faculties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/public-adjunct-faculty/public-adjunct-faculties.html',
                    controller: 'PublicAdjunctFacultyController',
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
        .state('public-lecturer-profile-detail', {
            parent: 'study',
            url: '/public-lecturer-profile/{id}',
            data: {
                authorities: [],
                pageTitle: 'Lecturer Profile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/public-adjunct-faculty/public-lecturer-profile.html',
                    controller: 'PublicLecturerProfileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PublicLecturerProfile', function($stateParams, PublicLecturerProfile) {
                    return PublicLecturerProfile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'public-adjunct-faculty',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }

})();
