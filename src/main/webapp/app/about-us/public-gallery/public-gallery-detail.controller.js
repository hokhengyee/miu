(function() {
	'use strict';

	angular.module('miuApp').controller('PublicGalleryDetailController',
			PublicGalleryDetailController);

	PublicGalleryDetailController.$inject = [ '$scope', '$rootScope',
			'$stateParams', 'previousState', 'DataUtils', 'entity',
			'PublicGallery' ];

	function PublicGalleryDetailController($scope, $rootScope, $stateParams,
			previousState, DataUtils, entity, PublicGallery) {
		var vm = this;

		vm.gallery = entity;
		vm.previousState = previousState.name;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		var unsubscribe = $rootScope.$on('miuApp:galleryUpdate', function(
				event, result) {
			vm.gallery = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
