(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'au-news-and-event',
						{
							parent : 'about-us',
							url : '/public-news-and-event?page&sort&search',
							data : {
								authorities : [],
								pageTitle : 'News And Events'
							},
							views : {
								'content@' : {
									templateUrl : 'app/about-us/public-news-events/public-news-events.html',
									controller : 'AUNewsAndEventController',
									controllerAs : 'vm'
								}
							},
							params : {
								page : {
									value : '1',
									squash : true
								},
								sort : {
									value : 'id,asc',
									squash : true
								},
								search : null
							},
							resolve : {
								pagingParams : [
										'$stateParams',
										'PaginationUtil',
										function($stateParams, PaginationUtil) {
											return {
												page : PaginationUtil
														.parsePage($stateParams.page),
												sort : $stateParams.sort,
												predicate : PaginationUtil
														.parsePredicate($stateParams.sort),
												ascending : PaginationUtil
														.parseAscending($stateParams.sort),
												search : $stateParams.search
											};
										} ]
							}
						})
				.state(
						'au-news-and-event-detail',
						{
							parent : 'about-us',
							url : '/public-news-and-event/{id}',
							data : {
								authorities : [],
								pageTitle : 'News And Event'
							},
							views : {
								'content@' : {
									templateUrl : 'app/about-us/public-news-events/public-news-events-detail.html',
									controller : 'AUNewsAndEventDetailController',
									controllerAs : 'vm'
								}
							},
							resolve : {
								entity : [ '$stateParams', 'AUNewsAndEvent',
										function($stateParams, AUNewsAndEvent) {
											return AUNewsAndEvent.get({
												id : $stateParams.id
											}).$promise;
										} ],
								previousState : [
										"$state",
										function($state) {
											var currentStateData = {
												name : $state.current.name
														|| 'au-news-and-event',
												params : $state.params,
												url : $state.href(
														$state.current.name,
														$state.params)
											};
											return currentStateData;
										} ]
							}
						});
	}

})();
