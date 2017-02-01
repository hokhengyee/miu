(function() {
	'use strict';

	angular.module('miuApp').controller('MyPaymentController',
			MyPaymentController);

	MyPaymentController.$inject = [ '$scope', '$state', 'DataUtils',
			'MyPayment', 'ParseLinks', 'AlertService' ];

	function MyPaymentController($scope, $state, DataUtils, MyPayment,
			ParseLinks, AlertService) {
		var vm = this;

		vm.payments = null;

		loadAll();

		function loadAll() {
			MyPayment.query({}, onSuccess, onError);

			function onSuccess(data, headers) {
				vm.studentPayments = data;
			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}
	}
})();
