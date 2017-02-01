(function() {
    'use strict';

    angular
        .module('miuApp')
        .controller('ResearchPaperDetailController', ResearchPaperDetailController);

    ResearchPaperDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ResearchPaper', 'Course'];

    function ResearchPaperDetailController($scope, $rootScope, $stateParams, previousState, entity, ResearchPaper, Course) {
        var vm = this;

        vm.researchPaper = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('miuApp:researchPaperUpdate', function(event, result) {
            vm.researchPaper = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
