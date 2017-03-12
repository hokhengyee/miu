(function() {
	'use strict';

	angular.module('miuApp').controller('PublicGalleryController',
			PublicGalleryController);

	PublicGalleryController.$inject = [ '$scope', '$state', 'DataUtils',
			'PublicGallery', 'ParseLinks', 'AlertService',
			'paginationConstants', 'pagingParams', 'entity', '$sce' ];

	function PublicGalleryController($scope, $state, DataUtils, PublicGallery,
			ParseLinks, AlertService, paginationConstants, pagingParams,
			entity, $sce) {
		var vm = this;

		vm.loadPage = loadPage;
		vm.predicate = pagingParams.predicate;
		vm.reverse = pagingParams.ascending;
		vm.transition = transition;
		vm.itemsPerPage = paginationConstants.itemsPerPage;
		vm.openFile = DataUtils.openFile;
		vm.byteSize = DataUtils.byteSize;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

		loadAll();

		function loadAll() {
			PublicGallery.query({
				page : pagingParams.page - 1,
				size : vm.itemsPerPage,
				sort : sort()
			}, onSuccess, onError);
			function sort() {
				var result = [ vm.predicate + ','
						+ (vm.reverse ? 'asc' : 'desc') ];
				if (vm.predicate !== 'id') {
					result.push('id');
				}
				return result;
			}
			function onSuccess(data, headers) {
				vm.links = ParseLinks.parse(headers('link'));
				vm.totalItems = headers('X-Total-Count');
				vm.queryCount = vm.totalItems;
				vm.galleries = data;
				vm.page = pagingParams.page;
			}
			function onError(error) {
				AlertService.error(error.data.message);
			}
		}

		function loadPage(page) {
			vm.page = page;
			vm.transition();
		}

		function transition() {
			$state.transitionTo($state.$current, {
				page : vm.page,
				sort : vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
				search : vm.currentSearch
			});
		}
	}
})();
