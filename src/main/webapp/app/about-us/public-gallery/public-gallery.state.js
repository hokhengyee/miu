(function() {
	'use strict';

	angular.module('miuApp').config(stateConfig);

	stateConfig.$inject = [ '$stateProvider' ];

	function stateConfig($stateProvider) {
		$stateProvider
				.state(
						'public-gallery',
						{
							parent : 'about-us',
							url : '/public-gallery?page&sort&search',
							data : {
								authorities : [],
								pageTitle : 'Gallery'
							},
							views : {
								'content@' : {
									templateUrl : 'app/about-us/public-gallery/public-galleries.html',
									controller : 'PublicGalleryController',
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
						'public-gallery-detail',
						{
							parent : 'entity',
							url : '/public-gallery/{id}',
							data : {
								authorities : [],
								pageTitle : 'Gallery'
							},
							views : {
								'content@' : {
									templateUrl : 'app/about-us/public-gallery/public-gallery-detail.html',
									controller : 'PublicGalleryDetailController',
									controllerAs : 'vm'
								}
							},
							resolve : {
								entity : [ '$stateParams', 'PublicGallery',
										function($stateParams, PublicGallery) {
											return PublicGallery.get({
												id : $stateParams.id
											}).$promise;
										} ],
								previousState : [
										"$state",
										function($state) {
											var currentStateData = {
												name : $state.current.name
														|| 'gallery',
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
