(function() {
	'use strict';

	angular.module('miuApp').controller('HomeController', HomeController);

	HomeController.$inject = [ '$scope', 'Principal', 'LoginService', '$state',
			'entity', 'HomeMsg', '$sce' ];

	function HomeController($scope, Principal, LoginService, $state, entity,
			HomeMsg, $sce) {
		var vm = this;

		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

		vm.account = null;
		vm.isAuthenticated = null;
		vm.login = LoginService.open;
		vm.register = register;
		$scope.$on('authenticationSuccess', function() {
			getAccount();
		});

		getAccount();

		function getAccount() {
			Principal.identity().then(function(account) {
				vm.account = account;
				vm.isAuthenticated = Principal.isAuthenticated;
			});
		}

		function register() {
			$state.go('register');
		}
	}
})();
