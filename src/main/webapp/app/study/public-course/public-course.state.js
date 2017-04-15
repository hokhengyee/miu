(function() {
    'use strict';

    angular
        .module('miuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('public-course', {
            parent: 'study',
            url: '/study/courses-and-fees?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'Courses & Fees'
            },
            views: {
                'content@': {
                	templateUrl: 'app/study/public-course/public-courses.html',
                    controller: 'PublicCourseController',
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
                }],
                entity : [ '$stateParams', 'RefundPolicyMsg',
					function($stateParams, RefundPolicyMsg) {
						return RefundPolicyMsg.get({
							id : $stateParams.id
						}).$promise;
				} ]
            }
        })
        .state('public-course-detail', {
            parent: 'study',
            url: '/study/course/{id}',
            data: {
                authorities: [],
                pageTitle: 'Course'
            },
            views: {
                'content@': {
                    templateUrl: 'app/study/public-course/public-course-detail.html',
                    controller: 'PublicCourseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PublicCourse', function($stateParams, PublicCourse) {
                    return PublicCourse.get({id : $stateParams.id}).$promise;
                }],
                entity2: ['$stateParams', 'PublicEntryQualification', 
                	function($stateParams, PublicEntryQualification) {
                    return PublicEntryQualification.get({id : $stateParams.id}).$promise;
                }],
                entity3: ['$stateParams', 'PublicModuleTheological', 
                	function($stateParams, PublicModuleTheological) {
                    return PublicModuleTheological.get({id : $stateParams.id}).$promise;
                }],
                entity4: ['$stateParams', 'PublicModulePracticalMinistry', 
                	function($stateParams, PublicModulePracticalMinistry) {
                    return PublicModulePracticalMinistry.get({id : $stateParams.id}).$promise;
                }],
                entity5: ['$stateParams', 'PublicResearchPaper', 
                	function($stateParams, PublicResearchPaper) {
                    return PublicResearchPaper.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'course',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }

})();
