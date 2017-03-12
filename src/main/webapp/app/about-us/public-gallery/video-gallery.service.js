(function() {
	'use strict';
	angular.module('miuApp').factory('VideoGalleryMsg', VideoGalleryMsg);

	VideoGalleryMsg.$inject = [ '$resource' ];

	function VideoGalleryMsg($resource) {
		var resourceUrl = 'api/public/video-gallery';

		return $resource(resourceUrl, {}, {
			'get' : {
				method : 'GET',
				transformResponse : function(data) {
					if (data) {
						data = angular.fromJson(data);
					}

					return data;
				}
			}
		});
	}
})();
