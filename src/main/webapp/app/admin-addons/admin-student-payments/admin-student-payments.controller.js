(function() {
	'use strict';

	angular.module('miuApp').controller('AdminStudentPaymentsController',
			AdminStudentPaymentsController);

	AdminStudentPaymentsController.$inject = [ '$scope', '$state',
			'$stateParams', 'DataUtils', 'ParseLinks', 'AlertService',
			'previousState', 'AdminStudentPayments', 'AdminFindUser' ];

	function AdminStudentPaymentsController($scope, $state, $stateParams,
			DataUtils, ParseLinks, AlertService, previousState,
			AdminStudentPayments, AdminFindUser) {
		var vm = this;

		vm.previousState = previousState.name;

		vm.payments = null;
		vm.studentPayments = null;
		vm.user = null;

		loadAll();

		function loadAll() {
			AdminFindUser.query({
				id : $stateParams.id,
			}, onAFUSuccess, onAFUError);

			function onAFUSuccess(data, headers) {
				vm.user = data;
			}

			function onAFUError(error) {
				AlertService.error(error.data.message);
			}

			AdminStudentPayments.query({
				id : $stateParams.id,
			}, onSuccess, onError);

			function onSuccess(data, headers) {
				vm.studentPayments = data;
			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}
	}
})();
