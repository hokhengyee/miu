(function() {
	'use strict';

	angular.module('miuApp').controller('HomeController', HomeController);

	HomeController.$inject = [ '$scope', 'Principal', 'LoginService', '$state',
			'entity', 'HomeMsg', 'HomeNewsAndEvents', 'PublicPageViewLog',
			'$sce' ];

	function HomeController($scope, Principal, LoginService, $state, entity,
			HomeMsg, HomeNewsAndEvents, PublicPageViewLog, $sce) {
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

		logPageVisit();
		getAccount();
		getNewsAndEvents();

		function logPageVisit() {
			PublicPageViewLog.get();
		}

		function getAccount() {
			Principal.identity().then(function(account) {
				vm.account = account;
				vm.isAuthenticated = Principal.isAuthenticated;
			});
		}

		function getNewsAndEvents() {
			vm.newsAndEvents = HomeNewsAndEvents.get();
		}

		function register() {
			$state.go('register');
		}

		/* Carousel */
		$scope.myInterval = 5000;
		$scope.noWrapSlides = false;
		$scope.active = 0;
		var slides = $scope.slides = [];
		var currIndex = 0;

		$scope.addSlide = function() {
			var newWidth = 600 + slides.length + 1;
			slides.push({
				image : '/content/images/banner/banner' + currIndex + '.jpg',
				// text : [ 'Nice image', 'Awesome photograph', 'That is so
				// cool',
				// 'I love that' ][slides.length % 4],
				id : currIndex++
			});
		};

		$scope.randomize = function() {
			var indexes = generateIndexesArray();
			assignNewIndexesToSlides(indexes);
		};

		for (var i = 0; i < 4; i++) {
			$scope.addSlide();
		}

		// Randomize logic below

		function assignNewIndexesToSlides(indexes) {
			for (var i = 0, l = slides.length; i < l; i++) {
				slides[i].id = indexes.pop();
			}
		}
	}
})();
