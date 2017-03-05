(function() {
	'use strict';

	angular.module('miuApp').controller('AdminStudentGradingsController',
			AdminStudentGradingsController);

	AdminStudentGradingsController.$inject = [ '$stateParams', 'Auth',
			'Principal', 'GradingMsg', '$sce', 'AdminPracticalMinistryResult',
			'AdminTheologicalResult', 'AdminResearchPaperResult',
			'AdminBookReviewResult', 'AdminDissertationResult',
			'AdminArticleResult', 'AdminSermonResult', 'AdminFindUser',
			'previousState' ];

	function AdminStudentGradingsController($stateParams, Auth, Principal,
			GradingMsg, $sce, AdminPracticalMinistryResult,
			AdminTheologicalResult, AdminResearchPaperResult,
			AdminBookReviewResult, AdminDissertationResult, AdminArticleResult,
			AdminSermonResult, AdminFindUser, previousState) {

		var vm = this;

		vm.previousState = previousState.name;

		vm.load = load;
		vm.practicalMinistryResults = [];
		vm.theologicalResults = [];
		vm.researchPapers = [];
		vm.bookReviews = [];
		vm.dissertations = [];
		vm.writingArticles = [];
		vm.sermonOutlines = [];

		load();

		function load() {
			AdminFindUser.query({
				id : $stateParams.id,
			}, onAFUSuccess, onAFUError);

			function onAFUSuccess(data, headers) {
				vm.user = data;

				AdminPracticalMinistryResult.get({
					id : vm.user.id
				}, function(result) {
					vm.practicalMinistryResults = result;
				}, function(response) {
					console.log("No Practical Ministry Results!");
				});

				AdminTheologicalResult.get({
					id : vm.user.id
				}, function(result) {
					vm.theologicalResults = result;
				}, function(response) {
					console.log("No Theological Results!");
				});

				AdminResearchPaperResult.get({
					id : vm.user.id
				}, function(result) {
					vm.researchPapers = result;
				}, function(response) {
					console.log("No Research Paper Results!");
				});

				AdminBookReviewResult.get({
					id : vm.user.id
				}, function(result) {
					vm.bookReviews = result;
				}, function(response) {
					console.log("No Book Review Results!");
				});

				AdminDissertationResult.get({
					id : vm.user.id
				}, function(result) {
					vm.dissertations = result;
				}, function(response) {
					console.log("No Dissertation Results!");
				});

				AdminArticleResult.get({
					id : vm.user.id
				}, function(result) {
					vm.writingArticles = result;
				}, function(response) {
					console.log("No Article Results!");
				});

				AdminSermonResult.get({
					id : vm.user.id
				}, function(result) {
					vm.sermonOutlines = result;
				}, function(response) {
					console.log("No Sermon Results!");
				});
			}

			function onAFUError(error) {
				AlertService.error(error.data.message);
			}
		}
	}
})();
