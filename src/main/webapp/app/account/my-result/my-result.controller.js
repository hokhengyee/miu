(function() {
	'use strict';

	angular.module('miuApp').controller('MyResultController',
			MyResultController);

	MyResultController.$inject = [ '$stateParams', 'Auth', 'Principal',
			'MyPracticalMinistryResult', 'MyTheologicalResult',
			'MyResearchPaperResult' ];

	function MyResultController($stateParams, Auth, Principal,
			MyPracticalMinistryResult, MyTheologicalResult,
			MyResearchPaperResult) {
		var vm = this;

		vm.load = load;
		vm.practicalMinistryResults = [];
		vm.theologicalResults = [];
		vm.researchPapers = [];
		vm.bookReviews = [];
		vm.dissertations = [];
		vm.writingArticles = [];
		vm.sermonOutlines = [];

		Principal.identity().then(function(account) {
			vm.account = account;
			vm.load(account.login);
		});

		function load(login) {
			MyPracticalMinistryResult.get({
				login : login
			}, function(result) {
				vm.practicalMinistryResults = result;
			}, function(response) {
				console.log("No Practical Ministry Results!");
			});

			MyTheologicalResult.get({
				login : login
			}, function(result) {
				vm.theologicalResults = result;
			}, function(response) {
				console.log("No Theological Results!");
			});

			MyResearchPaperResult.get({
				login : login
			}, function(result) {
				vm.researchPapers = result;
			}, function(response) {
				console.log("No Research Paper Results!");
			});
		}
	}
})();
