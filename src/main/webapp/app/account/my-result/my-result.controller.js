(function() {
	'use strict';

	angular.module('miuApp').controller('MyResultController',
			MyResultController);

	MyResultController.$inject = [ '$stateParams', 'Auth', 'Principal',
			'GradingMsg', 'entity', '$sce', 'MyPracticalMinistryResult',
			'MyTheologicalResult', 'MyResearchPaperResult',
			'MyBookReviewResult', 'MyDissertationResult', 'MyArticleResult',
			'MySermonResult' ];

	function MyResultController($stateParams, Auth, Principal, GradingMsg,
			entity, $sce, MyPracticalMinistryResult, MyTheologicalResult,
			MyResearchPaperResult, MyBookReviewResult, MyDissertationResult,
			MyArticleResult, MySermonResult) {
		
		var vm = this;
		
		vm.staticPage = entity;
		vm.staticHtml = $sce.trustAsHtml(vm.staticPage.content);

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

			MyBookReviewResult.get({
				login : login
			}, function(result) {
				vm.bookReviews = result;
			}, function(response) {
				console.log("No Book Review Results!");
			});

			MyDissertationResult.get({
				login : login
			}, function(result) {
				vm.dissertations = result;
			}, function(response) {
				console.log("No Dissertation Results!");
			});

			MyArticleResult.get({
				login : login
			}, function(result) {
				vm.writingArticles = result;
			}, function(response) {
				console.log("No Article Results!");
			});

			MySermonResult.get({
				login : login
			}, function(result) {
				vm.sermonOutlines = result;
			}, function(response) {
				console.log("No Sermon Results!");
			});
		}
	}
})();
