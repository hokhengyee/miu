(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('StudentResearchPaperResultDetailController', StudentResearchPaperResultDetailController);

    StudentResearchPaperResultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentResearchPaperResult', 'ResearchPaper', 'User'];

    function StudentResearchPaperResultDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentResearchPaperResult, ResearchPaper, User) {
        var vm = this;

        vm.studentResearchPaperResult = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:studentResearchPaperResultUpdate', function(event, result) {
            vm.studentResearchPaperResult = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
